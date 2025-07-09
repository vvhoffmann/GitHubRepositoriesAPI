package com.hoffmann.githubrepositories.apivalidation;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class ApiLimitErrorHandler {

    @ExceptionHandler(ApiLimitExceededException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @JsonSerialize()
    public ErrorResponse handleUserValidationException(ApiLimitExceededException exception) {
        log.warn("Api limit exceeded");
        return new ErrorResponse(exception.getStatus(), exception.getMessage());
    }
}