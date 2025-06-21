package com.project.HistoryLine.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "T_CHARACTER_EVENTS_CACHE")
public class CharacterEventsCache {

    @Id
    @Column(nullable = false, name = "ID_CHARACTER_EVENT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "EVENT_NAME")
    private String eventName;

    @Column(nullable = false, name = "EVENT_DESCRIPTION", length = 2000)
    private String eventDescription;

    @Column(nullable = false, name = "EVENT_DATE")
    private Date eventDate;

    @Column(nullable = false, name = "IS_BEFORE_CHRIST")
    private boolean isBeforeChrist;

    @ManyToOne
    @JoinColumn(name="ID_CHARACTER", nullable=false)
    private CharacterCache fkCharacterCache;

}
