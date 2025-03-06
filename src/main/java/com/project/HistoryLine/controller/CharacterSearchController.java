package com.project.HistoryLine.controller;

import com.project.HistoryLine.dto.SearchItem;
import com.project.HistoryLine.dto.request.SuggestRequest;
import com.project.HistoryLine.dto.response.CharacterResponse;
import com.project.HistoryLine.dto.response.WikimediaResponse;
import com.project.HistoryLine.service.CharacterSearchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/character")
public class CharacterSearchController {

    private final CharacterSearchService service;

    public CharacterSearchController(CharacterSearchService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<WikimediaResponse> suggestResults(@Valid @RequestBody SuggestRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.suggestResults(request));
    }

    @GetMapping("/find-character")
    public ResponseEntity<CharacterResponse> findCharacter(@Valid @RequestBody SearchItem request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.findCharacter(request));
    }

}
