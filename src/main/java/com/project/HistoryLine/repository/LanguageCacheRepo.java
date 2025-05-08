package com.project.HistoryLine.repository;

import com.project.HistoryLine.model.LanguageCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageCacheRepo extends JpaRepository<LanguageCache, Long> {

    LanguageCache findLanguageCacheByName(String name);

}
