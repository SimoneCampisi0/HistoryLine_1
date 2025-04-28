package com.project.HistoryLine.service.cache;

import com.project.HistoryLine.model.CharacterCache;
import com.project.HistoryLine.model.CharacterEventsCache;
import com.project.HistoryLine.repository.CharacterCacheRepo;
import com.project.HistoryLine.repository.CharacterEventsCacheRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CronCacheService {

    @Autowired
    private CharacterCacheRepo characterRepo;

    @Autowired
    private CharacterEventsCacheRepo characterEventsRepo;

    /**
     * CronJob che elimina dalla cache i Character e gli CharacterEvents a essi associati più vecchi di 5 giorni.
     */
    @Scheduled(cron = "0 0 0 * * Sun")
    public void clearCache() {
        log.info("--- RUN CRON JOB: Clear Cache ---");
        log.info("--- START ---");
        List<CharacterCache> list = characterRepo.findAllOlderEqualsThan5Days();
        int sizeListCharacter = list.size(), sizeListEvent = 0;
        for(CharacterCache cc : list) {
            List<CharacterEventsCache> events = characterEventsRepo.findAllByFkCharacterCache(cc);
            sizeListEvent = events.size();
            characterEventsRepo.deleteAll(events);
        }
        characterRepo.deleteAll(list);
        log.info("--- Deleted {} CharacterCache and {} CharacterEventsCache ---", sizeListCharacter, sizeListEvent);
        log.info("--- END ---");
    }

}
