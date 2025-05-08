package com.project.HistoryLine.dto.request;

import lombok.Getter;

import java.sql.Date;

@Getter
public class SuggestRequest {

    private String name;

    private String description;

    private Date startDate;

    private Date endDate;

    private String languageName;

}
