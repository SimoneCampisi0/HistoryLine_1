package com.project.HistoryLine.service;

import com.project.HistoryLine.repository.CharacterRepository;
import org.springframework.stereotype.Service;

@Service
public class CharacterSearchService {

    private final CharacterRepository repository;

    public CharacterSearchService(CharacterRepository repository) {
        this.repository = repository;
    }

}
