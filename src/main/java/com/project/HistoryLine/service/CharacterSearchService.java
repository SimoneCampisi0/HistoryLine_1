package com.project.HistoryLine.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.HistoryLine.dto.request.SuggestRequest;
import com.project.HistoryLine.dto.SearchItem;
import com.project.HistoryLine.dto.response.WikimediaResponse;
import com.project.HistoryLine.exceptions.BusinessLogicException;
import com.project.HistoryLine.rdf4j.CharacterDao;
import com.project.HistoryLine.rdf4j.dto.SuggestRDFJ4Response;
import com.project.HistoryLine.utils.enums.ExceptionLevelEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CharacterSearchService {

    @Autowired
    private WikimediaService wikimediaService;

    @Value("${wikimedia.resource.url}")
    private String wikimediaResourceUrl;

    @Autowired
    private CharacterDao characterDao;

    private String getCharacterNameFromLink(SearchItem item) {
        return item.getLink() != null ? item.getLink().substring(30) : null;
    }

    private List<String> splitText(String text) {
        String marker = "== Note ==";
        int index = text.indexOf(marker);
        if(index != -1) {
            text = text.substring(0, index);
        }

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
     * Partendo da un singolo risultato (SearchItem individuato dall'API sottostante)
     * ottiene il wikitesto da inviare successivamente alle API di OpenAI. TODO
     * @param item
     * @return
     * @throws Exception
     */
    public List<String> findCharacter(SearchItem item) throws BusinessLogicException {
        Map<String, String> params = new HashMap<>();
        params.put("character_name", getCharacterNameFromLink(item));
        params.put("format", "json");

        try {
            String wikimedia_body = wikimediaService.getElements(wikimediaResourceUrl, params);
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
            log.info("wikitext {}", wikiText);


            List<String> splittedWikiText = splitText(wikiText);
            if(splittedWikiText.isEmpty()) {
                throw new BusinessLogicException("Text splitting failed", "Text splitting phase failed", ExceptionLevelEnum.ERROR);
            }
            return splittedWikiText;
        } catch (Exception ex) {
            throw new BusinessLogicException("Error in find character", "Single character not found", ExceptionLevelEnum.ERROR);
        }
    }


    /**
     * Esegue una ricerca su Wikimedia in base al nome del personaggio.
     * Lancia un'eccezione se il nome è nullo o vuoto, esegue la chiamata al servizio,
     * deserializza il JSON e crea un WikimediaResponse con la keyword e i risultati.
     * TODO: aggiungere eventuale paginazione e limite numerico ai risultati
     */

    public WikimediaResponse suggestResults(SuggestRequest request) throws BusinessLogicException {
        if(request.getName() == null || request.getName().isEmpty()) {
            log.error("Character name empty");
            throw new BusinessLogicException("Character name empty", "Character name is empty", ExceptionLevelEnum.ERROR);
        }

        try {
            List<SuggestRDFJ4Response> suggestList = characterDao.executeFindCharacterQuery(request.getName());
            return WikimediaResponse.builder()
                    .searchedKeyword(request.getName())
                    .items(suggestList)
                    .build();
        } catch(Exception e) {
            throw new BusinessLogicException("Error in find list of character", "List of suggest characters not found", ExceptionLevelEnum.ERROR);
        }

        /*
        String url = wikimediaResourceUrl + "?action=opensearch&search=" + request.getName();

        try {
            String jsonResponse = wikimediaService.getElements(url);
            ObjectMapper mapper = new ObjectMapper();
            List<Object> rawData = mapper.readValue(jsonResponse, List.class);

            String keyword = (String) rawData.get(0);
            List<String> results = (List<String>) rawData.get(1);
            List<String> extraOptions = (List<String>) rawData.get(2);
            List<String> links = (List<String>) rawData.get(3);

            List<SearchItem> items = new ArrayList<>();

            for(int i = 0; i < results.size(); i++) {
                if(results.get(i) == null || links.get(i) == null) {
                    throw new BusinessLogicException("Error in suggest results", "Result is null", ExceptionLevelEnum.ERROR);
                }
                String result = results.get(i);
                String extraOption = extraOptions.get(i);
                String link = links.get(i);

                SearchItem item = SearchItem.builder()
                        .result(result)
                        .extraOption(extraOption)
                        .link(link)
                        .build();
                items.add(item);
            }

            WikimediaResponse wikimediaResponse = WikimediaResponse.builder()
                    .searchedKeyword(keyword)
                    .items(items)
                    .build();

            log.info("response: {}", wikimediaResponse);
            return wikimediaResponse;
        } catch (Exception e) {
            throw new BusinessLogicException("Error in find list of character", "List of suggest characters not found", ExceptionLevelEnum.ERROR);
        }
         */
    }

}
