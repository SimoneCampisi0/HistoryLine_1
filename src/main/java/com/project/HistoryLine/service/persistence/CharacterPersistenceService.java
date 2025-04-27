package com.project.HistoryLine.service.persistence;

import com.project.HistoryLine.dto.CharacterEventsDTO;
import com.project.HistoryLine.dto.SearchItem;
import com.project.HistoryLine.exceptions.BusinessLogicException;
import com.project.HistoryLine.model.Character;
import com.project.HistoryLine.model.TCharacterEvents;
import com.project.HistoryLine.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CharacterPersistenceService {

    @Autowired
    private CharacterRepository repository;

    @Autowired
    private CharacterEventsPersistenceService characterEventsPersistenceService;

    private List<TCharacterEvents> mapCharacterEvents(List<CharacterEventsDTO> events) {
        List<TCharacterEvents> list = new ArrayList<>();
        for(CharacterEventsDTO event : events) {
            TCharacterEvents characterEvent = TCharacterEvents.builder()
                    .eventName(event.getEventName())
                    .eventDate(event.getEventDate())
                    .eventDescription(event.getEventDescription())
                    .build();
            list.add(characterEvent);
        }
        return list;
    }

    public void saveCharacter(SearchItem item, String wikitext, List<CharacterEventsDTO> events) throws BusinessLogicException {
        List<TCharacterEvents> tCharacterEvents = mapCharacterEvents(events);
        Character character = Character.builder()
                .name(item.getResult())
                .link(item.getLink())
                .characterEventsDTOList(tCharacterEvents)
                .saveDate(new Date())
                .build();
        repository.save(character);
        for(TCharacterEvents event : tCharacterEvents) {
            event.setFkCharacter(character);
        }
        characterEventsPersistenceService.saveCharacterEvents(tCharacterEvents);
    }

    public Character findByLink(String link) {
        return repository.findCharacterByLink(link);
    }

    public List<CharacterEventsDTO> findCharacterEvents(Character character) {
        return characterEventsPersistenceService.findCharacterEvents(character);
    }

}
