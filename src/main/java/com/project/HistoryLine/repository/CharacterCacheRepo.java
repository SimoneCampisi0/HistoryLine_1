package com.project.HistoryLine.repository;

import com.project.HistoryLine.model.CharacterCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterCacheRepo extends JpaRepository<CharacterCache, Integer> {

    CharacterCache findCharacterByLink(String link);

    @Query(
            value = "SELECT * FROM T_CHARACTER_CACHE tcc WHERE DATEDIFF(NOW(), tcc.save_date) >= 5",
            nativeQuery = true
    )
    List<CharacterCache> findAllOlderEqualsThan5Days();
}
