package com.project.HistoryLine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class WikimediaService {

    @Autowired
    private RestTemplate restTemplate;

    public String getElements(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
    }

    public String getElements(String url, Map<String, String> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        if (params == null || params.isEmpty()) {
            throw new IllegalArgumentException("params cannot be null or empty");
        }

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("action", "query")
                .queryParam("prop", "revisions")
                .queryParam("titles", params.get("character_name"))
                .queryParam("rvslots", "*")
                .queryParam("rvprop", "content")
                .queryParam("formatversion", "2")
                .queryParam("format", params.get("format"))
                .build();
        String finalUrl = uriComponents.toUriString();

        ResponseEntity<String> response = restTemplate.exchange(
                finalUrl,
                HttpMethod.GET,
                entity,
                String.class,
                params
        );

        return response.getBody();
    }

}
