package com.project.HistoryLine.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "T_CHARACTER_CACHE")
public class CharacterCache {

    @Id
    @Column(nullable = false, name = "ID_CHARACTER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_character;

    @Column(nullable = false, name = "NAME")
    private String name;

    @Column(nullable = false, name = "LINK")
    private String link;

    @Column(nullable = false, name = "SAVE_DATE")
    private Date saveDate;

    @OneToMany(mappedBy = "fkCharacterCache")
    private List<CharacterEventsCache> characterEventsDTOList;
}
