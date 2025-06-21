package com.project.HistoryLine.repository;

import com.project.HistoryLine.model.CharacterDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterDetailsRepository extends JpaRepository<CharacterDetails, Integer> {
}
