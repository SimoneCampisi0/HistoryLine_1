package com.project.HistoryLine.service.cache;

import com.project.HistoryLine.dto.CharacterEventsDTO;
import com.project.HistoryLine.model.CharacterCache;
import com.project.HistoryLine.model.CharacterEventsCache;
import com.project.HistoryLine.repository.CharacterEventsCacheRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterEventsCacheService {

    @Autowired
    private CharacterEventsCacheRepo characterEventsCacheRepo;

    public void saveCharacterEvents(List<CharacterEventsCache> events) {
        characterEventsCacheRepo.saveAll(events);
    }

    /**
     * Metodo che restituisce dal DB i CharacterEvents di un CharacterCache salvati in cache
     * @param characterCache
     * @return Lista cache di CharacterEvents
     */
    public List<CharacterEventsDTO> findCharacterEvents(CharacterCache characterCache) {
        List<CharacterEventsCache> tEvents = characterEventsCacheRepo.findAllByFkCharacterCache(characterCache);
        List<CharacterEventsDTO> list = new ArrayList<>();
        for(CharacterEventsCache event: tEvents) {
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
