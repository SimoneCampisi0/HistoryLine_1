package com.project.HistoryLine.service.persistence;

import com.project.HistoryLine.dto.CharacterEventsDTO;
import com.project.HistoryLine.model.Character;
import com.project.HistoryLine.model.TCharacterEvents;
import com.project.HistoryLine.repository.TCharacterEventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterEventsPersistenceService {

    @Autowired
    private TCharacterEventsRepository tCharacterEventsRepository;

    public void saveCharacterEvents(List<TCharacterEvents> events) {
        tCharacterEventsRepository.saveAll(events);
    }

    public List<CharacterEventsDTO> findCharacterEvents(Character character) {
        List<TCharacterEvents> tEvents = tCharacterEventsRepository.findAllByFkCharacter(character);
        List<CharacterEventsDTO> list = new ArrayList<>();
        for(TCharacterEvents event: tEvents) {
            CharacterEventsDTO dto = CharacterEventsDTO.builder()
                    .eventDate(event.getEventDate())
                    .eventDescription(event.getEventDescription())
                    .eventName(event.getEventName())
                    .build();
            list.add(dto);
        }
        return list;
    }
}
