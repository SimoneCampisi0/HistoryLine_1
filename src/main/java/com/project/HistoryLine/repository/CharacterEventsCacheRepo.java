package com.project.HistoryLine.repository;

import com.project.HistoryLine.model.CharacterCache;
import com.project.HistoryLine.model.CharacterEventsCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterEventsCacheRepo extends JpaRepository<CharacterEventsCache, Long> {

    List<CharacterEventsCache> findAllByFkCharacterCache(CharacterCache fkCharacterCache);
    
}
