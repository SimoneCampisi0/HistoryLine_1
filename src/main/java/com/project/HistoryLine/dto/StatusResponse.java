package com.project.HistoryLine.dto;

import com.project.HistoryLine.utils.enums.ExceptionLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusResponse {

    private String title;

    private String message;

    private ExceptionLevelEnum level;

}

