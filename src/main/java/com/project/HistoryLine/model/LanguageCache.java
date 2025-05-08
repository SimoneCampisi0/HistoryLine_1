package com.project.HistoryLine.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "T_LANGUAGE_CACHE")
public class LanguageCache {

    @Id
    @Column(nullable = false, name = "ID_LANGUAGE_CACHE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "NAME")
    private String name;

    @Column(nullable = false, name = "WIKIPEDIA_LINK")
    private String link;

    @OneToMany(mappedBy = "fkLanguageCache")
    private List<CharacterCache> characterCacheList;
}
