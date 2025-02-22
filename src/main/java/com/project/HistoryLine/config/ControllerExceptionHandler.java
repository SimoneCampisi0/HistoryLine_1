package com.project.HistoryLine.config;

import com.project.HistoryLine.dto.StatusResponse;
import com.project.HistoryLine.exceptions.BusinessLogicException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<StatusResponse> handleBusinessLogicException (BusinessLogicException excep) {
        StatusResponse statusResponse = StatusResponse.builder()
                .title(excep.getTitle())
                .message(excep.getMessage())
                .level(excep.getLevel())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(statusResponse);
    }

}
