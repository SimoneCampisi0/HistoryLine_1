package com.project.HistoryLine.controller;

import com.project.HistoryLine.service.CharacterSearchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/character")
public class CharacterSearchController {

    private final CharacterSearchService service;

    public CharacterSearchController(CharacterSearchService service) {
        this.service = service;
    }

}
