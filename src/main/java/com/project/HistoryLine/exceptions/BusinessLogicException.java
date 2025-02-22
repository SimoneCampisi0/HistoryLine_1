package com.project.HistoryLine.exceptions;

import com.project.HistoryLine.utils.enums.ExceptionLevelEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessLogicException extends RuntimeException {

    protected final String title;

    protected final String message;

    protected final ExceptionLevelEnum level;

    public BusinessLogicException(String title, String message, ExceptionLevelEnum level) {
        this.title = title;
        this.message = message;
        this.level = level;
    }
}