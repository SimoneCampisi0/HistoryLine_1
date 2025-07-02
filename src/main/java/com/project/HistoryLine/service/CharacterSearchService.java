package com.project.HistoryLine.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.*;
import com.project.HistoryLine.dto.CharacterEventsDTO;
import com.project.HistoryLine.dto.request.SuggestRequest;
import com.project.HistoryLine.dto.SearchItem;
import com.project.HistoryLine.dto.response.CharacterDescriptionResp;
import com.project.HistoryLine.dto.response.CharacterResponse;
import com.project.HistoryLine.dto.response.WikimediaResponse;
import com.project.HistoryLine.exceptions.BusinessLogicException;
import com.project.HistoryLine.model.CharacterCache;
import com.project.HistoryLine.model.CharacterDetails;
import com.project.HistoryLine.model.LanguageCache;
import com.project.HistoryLine.rdf4j.CharacterDaoRDF4J;
import com.project.HistoryLine.rdf4j.dto.SuggestRDFJ4Response;
import com.project.HistoryLine.repository.LanguageCacheRepo;
import com.project.HistoryLine.service.cache.CharacterCacheService;
import com.project.HistoryLine.utils.enums.ExceptionLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;

@Service
@Slf4j
public class CharacterSearchService {

    @Autowired
    private OpenAIService openAiService;

    @Autowired
    private WikimediaService wikimediaService;

    @Autowired
    private CharacterCacheService characterCacheService;

    @Autowired
    private CharacterDaoRDF4J characterDaoRDF4J;

    @Autowired
    private LanguageCacheRepo languageCacheRepo;

    private String getCharacterNameFromLink(SearchItem item) {
        return item.getLink() != null ? item.getLink().substring(30) : null;
    }

    private List<String> splitText(String text) {
        List<String> splittedText = new ArrayList<>();
        int len = text.length();
        int sizeToken = len / 4;  // TODO: per il momento suddivido il token semplicemente in quattro
        for(int i = 0; i < len - sizeToken; i += sizeToken) {
            String buffer = text.substring(i, i+sizeToken);
            splittedText.add(buffer);
        }
        return splittedText;
    }

    /**
     * Metodo che si occupa di suddividere il wikitesto in blocchi da inviare a OpenAI.
     * @param wikitext
     * @return
     */
    private String simplifyWikitext(String wikitext) {
        String marker = "== Note ==";
        int index = wikitext.indexOf(marker);
        if(index != -1) {
            return wikitext.substring(0, index);
        }
        return wikitext;
    }

    /**
     * Partendo da un singolo risultato (SearchItem individuato dall'API sottostante)
     * ottiene il wikitesto da inviare successivamente alle API di OpenAI. TODO
     * @param item
     * @return
     * @throws Exception
     */
    private String findCharacter(SearchItem item, LanguageCache languageCache) throws BusinessLogicException {
        Map<String, String> params = new HashMap<>();
        params.put("character_name", getCharacterNameFromLink(item));
        params.put("format", "json");

        try {
            String wikimediaResourceLink = languageCache.getLink();
            String wikimedia_body = wikimediaService.getElements(wikimediaResourceLink, params);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(wikimedia_body);
            JsonNode wikitextNode = root.path("query")
                    .path("pages")
                    .get(0)
                    .path("revisions")
                    .get(0)
                    .path("slots")
                    .path("main")
                    .path("content");

            String wikiText = wikitextNode.asText().replaceAll("<[^>]+>", "");
            wikiText = simplifyWikitext(wikiText);
            return openAiService.generateOutput(languageCache, wikiText.replace("{{", "\\{\\{"));
        } catch (Exception ex) {
            throw new BusinessLogicException("Error in find character", "Single character not found", ExceptionLevelEnum.ERROR);
        }
    }

    private boolean setBeforeChristDate(String birthDate) {
        return birthDate.startsWith("-");
    }

    /**
     * Metodo che, una volta ottenuto il wikitesto, lo invia alle API di ChatGPT e ne casta la risposta in CharacterEventsDTO
     * @param item
     * @return
     * @throws BusinessLogicException
     */
    public CharacterResponse findCharacterEvents(SearchItem item) throws BusinessLogicException {
        CharacterCache characterCacheFound = characterCacheService.findByLink(item.getLink());
        if(!Objects.isNull(characterCacheFound)) {
            List<CharacterEventsDTO> eventList = characterCacheService.findCharacterEvents(characterCacheFound);
            CharacterDetails cd = characterCacheFound.getCharacterDetails();

            return CharacterResponse.builder()
                    .characterDescription(new CharacterDescriptionResp(characterCacheFound.getName(), cd.getDescription(), cd.getBornDateYear(), cd.getDeathDateYear()))
                    .characterEventDTOS(eventList)
                    .characterName(characterCacheFound.getName())
                    .build();
        }
        LanguageCache languageCache = languageCacheRepo.findLanguageCacheByName(item.getLanguageName());
        String respTextEvents = findCharacter(item, languageCache);
        log.info("wikitext {}", respTextEvents);

        // Esegue il parsing del testo fornito da OpenAI in JSON
        Gson parser = new Gson();
        Type listType = new TypeToken<List<CharacterEventsDTO>>() {}.getType();
        List<CharacterEventsDTO> list = parser.fromJson(respTextEvents, listType);

        if(list == null || list.isEmpty()) {
            throw new BusinessLogicException("No valid list", "Character event list not populated", ExceptionLevelEnum.ERROR);
        }

        for(CharacterEventsDTO e : list) {
            e.setEventIsBeforeChrist(setBeforeChristDate(e.getEventDate().toString()));
        }

        characterCacheService.saveCharacter(item, languageCache, list);
        log.info("found list: {}", list);

        CharacterDescriptionResp resp = findCharacterDescription(item);
        return CharacterResponse.builder()
                .characterEventDTOS(list)
                .characterDescription(resp)
                .build();
    }


    /**
     * Esegue una ricerca su Wikimedia in base al nome del personaggio.
     * Lancia un'eccezione se il nome è nullo o vuoto, esegue la chiamata al servizio,
     * deserializza il JSON e crea un WikimediaResponse con la keyword e i risultati.
     * TODO: aggiungere eventuale paginazione e limite numerico ai risultati
     */
    public WikimediaResponse suggestResults(SuggestRequest request) throws BusinessLogicException {
        if(request.getName() == null || request.getName().isEmpty()) {
            log.error("CharacterCache name empty");
            throw new BusinessLogicException("CharacterCache name empty", "CharacterCache name is empty", ExceptionLevelEnum.ERROR);
        }

        try {
            LanguageCache languageCache = languageCacheRepo.findLanguageCacheByName(request.getLanguageName());
            List<SuggestRDFJ4Response> suggestList = characterDaoRDF4J.executeFindCharacterQuery(request.getName(), languageCache.getName(), request.getPaginationRequest());
            Integer totalElements = characterDaoRDF4J.executeCountCharacterQuery(request.getName(), languageCache.getName());
            return WikimediaResponse.builder()
                    .searchedKeyword(request.getName())
                    .items(suggestList)
                    .totalElements(totalElements)
                    .build();
        } catch(Exception e) {
            throw new BusinessLogicException("Error in find list of character", "List of suggest characters not found", ExceptionLevelEnum.ERROR);
        }
    }

    public CharacterDescriptionResp findCharacterDescription(SearchItem item) {
        LanguageCache languageCache = languageCacheRepo.findLanguageCacheByName(item.getLanguageName());
        String description = openAiService.generateCharacterDescription(item.getResult(), languageCache);
        String jsonBornDeathYears = openAiService.generateCharacterBornDeathYear(item.getResult(), languageCache);

        Gson parser = new Gson();
        Type listType = new TypeToken<BornDeathDate>() {}.getType();
        BornDeathDate years = parser.fromJson(jsonBornDeathYears, listType);

        CharacterCache ch = characterCacheService.findByLink(item.getLink());
        CharacterDetails cd = CharacterDetails.builder()
                .description(description)
                .bornDateYear(years.getBorn())
                .deathDateYear(years.getDeath())
                .build();
        characterCacheService.saveCharacterDetails(cd);

        ch.setCharacterDetails(cd);
        characterCacheService.updateCharacter(ch);

        return CharacterDescriptionResp.builder()
                .characterName(item.getResult())
                .description(description)
                .bornYear(years.getBorn())
                .deathYear(years.getDeath())
                .build();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class BornDeathDate {
        private String born;
        private String death;
    }

}
