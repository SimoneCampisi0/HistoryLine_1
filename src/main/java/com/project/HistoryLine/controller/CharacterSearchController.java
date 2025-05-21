package com.project.HistoryLine.controller;

import com.project.HistoryLine.dto.CharacterEventsDTO;
import com.project.HistoryLine.dto.SearchItem;
import com.project.HistoryLine.dto.request.SuggestRequest;
import com.project.HistoryLine.dto.response.WikimediaResponse;
import com.project.HistoryLine.service.CharacterSearchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/character")
@CrossOrigin("https://focused-appreciation-production.up.railway.app")
public class CharacterSearchController {

    private final CharacterSearchService service;

    public CharacterSearchController(CharacterSearchService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<WikimediaResponse> suggestResults(@Valid @RequestBody SuggestRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(service.suggestResults(request));
    }

    @PostMapping("/find")
    public ResponseEntity<List<CharacterEventsDTO>> findCharacterEvents(@Valid @RequestBody SearchItem request){
        return ResponseEntity.status(HttpStatus.OK).body(service.findCharacterEvents(request));
    }

}
