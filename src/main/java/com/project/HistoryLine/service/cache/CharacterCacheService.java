package com.project.HistoryLine.service.cache;

import com.project.HistoryLine.dto.CharacterEventsDTO;
import com.project.HistoryLine.dto.SearchItem;
import com.project.HistoryLine.dto.response.CharacterResponse;
import com.project.HistoryLine.exceptions.BusinessLogicException;
import com.project.HistoryLine.model.CharacterCache;
import com.project.HistoryLine.model.CharacterEventsCache;
import com.project.HistoryLine.model.LanguageCache;
import com.project.HistoryLine.repository.CharacterCacheRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CharacterCacheService {

    @Autowired
    private CharacterCacheRepo repository;

    @Autowired
    private CharacterEventsCacheService characterEventsCacheService;

    private List<CharacterEventsCache> mapCharacterEvents(List<CharacterEventsDTO> events) {
        List<CharacterEventsCache> list = new ArrayList<>();
        for(CharacterEventsDTO event : events) {
            CharacterEventsCache characterEvent = CharacterEventsCache.builder()
                    .eventName(event.getEventName())
                    .eventDate(event.getEventDate())
                    .eventDescription(event.getEventDescription())
                    .build();
            list.add(characterEvent);
        }
        return list;
    }

    /**
     * Metodo che salva un Character ricercato nell'opportuna tabella di cache
     * @param item
     * @param events
     * @throws BusinessLogicException
     */
    public void saveCharacter(SearchItem item, LanguageCache languageCache, List<CharacterEventsDTO> events, String description) throws BusinessLogicException {
        List<CharacterEventsCache> characterEventCaches = mapCharacterEvents(events);
        CharacterCache characterCache = CharacterCache.builder()
                .name(item.getResult())
                .link(item.getLink())
                .characterEventsDTOList(characterEventCaches)
                .saveDate(new Date())
                .fkLanguageCache(languageCache)
                .description(description)
                .build();
        repository.save(characterCache);
        for(CharacterEventsCache event : characterEventCaches) {
            event.setFkCharacterCache(characterCache);
        }
        characterEventsCacheService.saveCharacterEvents(characterEventCaches);
    }

    public CharacterCache findByLink(String link) {
        return repository.findCharacterByLink(link);
    }

    public CharacterResponse findCharacterEvents(CharacterCache characterCache) {
        return CharacterResponse.builder()
                .characterName(characterCache.getName())
                .characterDescription(characterCache.getDescription())
                .characterEventDTOS(characterEventsCacheService.findCharacterEvents(characterCache))
                .build();
    }

}
