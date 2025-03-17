package com.project.HistoryLine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.HistoryLine.dto.request.SuggestRequest;
import com.project.HistoryLine.dto.SearchItem;
import com.project.HistoryLine.dto.response.CharacterResponse;
import com.project.HistoryLine.dto.response.WikimediaResponse;
import com.project.HistoryLine.exceptions.BusinessLogicException;
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

    private String getCharacterNameFromLink(SearchItem item) {
        return item.getLink() != null ? item.getLink().substring(30) : null;
    }

    /**
     * Partendo da un singolo risultato (SearchItem individuato dall'API sottostante)
     * ottiene il wikitesto da inviare successivamente alle API di OpenAI. TODO
     * @param item
     * @return
     * @throws Exception
     */

    public CharacterResponse findCharacter(SearchItem item) throws BusinessLogicException {
        Map<String, String> params = new HashMap<>();
        params.put("character_name", getCharacterNameFromLink(item));
        params.put("format", "json");

        try {
            String wikimedia_body = wikimediaService.getElements(wikimediaResourceUrl, params);
        } catch (Exception ex) {
            throw new BusinessLogicException("Error in find character", "Single character not found", ExceptionLevelEnum.ERROR);
        }
        return null;
    }


    /**
     * Esegue una ricerca su Wikimedia in base al nome del personaggio.
     * Lancia un'eccezione se il nome è nullo o vuoto, esegue la chiamata al servizio,
     * deserializza il JSON e crea un WikimediaResponse con la keyword e i risultati.
     */

    public WikimediaResponse suggestResults(SuggestRequest request) throws BusinessLogicException {
        if(request.getName() == null || request.getName().isEmpty()) {
            log.error("Character name empty");
            throw new BusinessLogicException("Character name empty", "Character name is empty", ExceptionLevelEnum.ERROR);
        }
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

            for(int i = 0; i < results.size(); i++){
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
    }

}
