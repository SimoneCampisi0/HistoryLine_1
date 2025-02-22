package com.project.HistoryLine.dto.request;

import lombok.Getter;

import java.sql.Date;

@Getter
public class CharacterSearchRequest {

    private String name;

    private String description;

    private Date startDate;

    private Date endDate;


}
