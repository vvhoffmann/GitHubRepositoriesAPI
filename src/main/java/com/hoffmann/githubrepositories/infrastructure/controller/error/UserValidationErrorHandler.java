package com.hoffmann.githubrepositories.infrastructure.controller.error;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hoffmann.githubrepositories.apivalidation.dto.ErrorResponse;
import com.hoffmann.githubrepositories.apivalidation.exceptions.UserNotFoundException;
import com.hoffmann.githubrepositories.infrastructure.controller.GitHubRepositoriesRestController;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = GitHubRepositoriesRestController.class)
@Log4j2
public class UserValidationErrorHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @JsonSerialize()
    public ErrorResponse handleUserValidationException(UserNotFoundException exception) {
        log.warn("User not found");
        return new ErrorResponse(exception.getStatus(), exception.getMessage());
    }

    @ExceptionHandler(ResultNotFindException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @JsonSerialize()
    public ErrorResponse handleResultNotFoundException(ResultNotFindException exception) {
        log.warn("Result not found");
        return new ErrorResponse(exception.getStatus(), exception.getMessage());
    }
}