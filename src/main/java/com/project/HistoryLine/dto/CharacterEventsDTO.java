package com.project.HistoryLine.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class CharacterEventsDTO {

    private String eventName;

    private String eventDescription;

    private Date eventDate;

}
