package com.project.HistoryLine.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "T_CHARACTER_CACHE_DETAILS")
public class CharacterDetails {

    @Id
    @Column(nullable = false, name = "ID_CHARACTER_DETAILS")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_character_details;

    @Column(nullable = false, name = "DESCRIPTION", length = 10000)
    private String description;

    @Column(nullable = true, name = "BORN_DATE_YEAR")
    private String bornDateYear;

    @Column(nullable = true, name = "DEATH_DATE_YEAR")
    private String deathDateYear;
}
