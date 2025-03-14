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
import java.util.List;

@Service
@Slf4j
public class CharacterSearchService {

    @Autowired
    private WikimediaService wikimediaService;

    @Value("${wikimedia.resource.url}")
    private String wikimediaResourceUrl;

    public CharacterResponse findCharacter(SearchItem item) throws Exception {
        String url = wikimediaResourceUrl + "?action=query&prop=revisions&titles=Carlo_Magno&rvslots=*&rvprop=content&formatversion=2&format=json";
        return null;
    }


    /**
     * Esegue una ricerca su Wikimedia in base al nome del personaggio.
     * Lancia un'eccezione se il nome è nullo o vuoto, esegue la chiamata al servizio,
     * deserializza il JSON e crea un WikimediaResponse con la keyword e i risultati.
     */

    public WikimediaResponse suggestResults(SuggestRequest request) throws Exception {
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
            throw new Exception(e);
        }
    }

}
