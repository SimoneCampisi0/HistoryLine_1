package com.project.HistoryLine.service.cache;

import com.project.HistoryLine.dto.CharacterEventsDTO;
import com.project.HistoryLine.dto.SearchItem;
import com.project.HistoryLine.exceptions.BusinessLogicException;
import com.project.HistoryLine.model.CharacterCache;
import com.project.HistoryLine.model.CharacterDetails;
import com.project.HistoryLine.model.CharacterEventsCache;
import com.project.HistoryLine.model.LanguageCache;
import com.project.HistoryLine.repository.CharacterCacheRepo;
import com.project.HistoryLine.repository.CharacterDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CharacterCacheService {

    @Autowired
    private CharacterDetailsRepository characterDetailsRepository;

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

    public CharacterDetails saveCharacterDetails(CharacterDetails characterDetails) throws BusinessLogicException {
        return characterDetailsRepository.save(characterDetails);
    }

    /**
     * Metodo che salva un Character ricercato nell'opportuna tabella di cache
     * @param item
     * @param events
     * @throws BusinessLogicException
     */
    public void saveCharacter(SearchItem item, LanguageCache languageCache, List<CharacterEventsDTO> events) throws BusinessLogicException {
        List<CharacterEventsCache> characterEventCaches = mapCharacterEvents(events);
        CharacterCache characterCache = CharacterCache.builder()
                .name(item.getResult())
                .link(item.getLink())
                .characterEventsDTOList(characterEventCaches)
                .saveDate(new Date())
                .fkLanguageCache(languageCache)
                .build();
        repository.save(characterCache);
        for(CharacterEventsCache event : characterEventCaches) {
            event.setFkCharacterCache(characterCache);
        }
        characterEventsCacheService.saveCharacterEvents(characterEventCaches);
    }

    public void updateCharacter(CharacterCache ch) {
        repository.save(ch);
    }

    public CharacterCache findByLink(String link) {
        return repository.findCharacterByLink(link);
    }

    public List<CharacterEventsDTO> findCharacterEvents(CharacterCache characterCache) {
        return characterEventsCacheService.findCharacterEvents(characterCache);
    }

}
