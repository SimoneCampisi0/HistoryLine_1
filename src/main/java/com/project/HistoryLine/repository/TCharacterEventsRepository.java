package com.project.HistoryLine.repository;

import com.project.HistoryLine.model.Character;
import com.project.HistoryLine.model.TCharacterEvents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TCharacterEventsRepository extends JpaRepository<TCharacterEvents, Long> {

    List<TCharacterEvents> findAllByFkCharacter(Character fkCharacter);
    
}
