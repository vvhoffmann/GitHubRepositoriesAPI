package com.hoffmann.githubrepositories.apivalidation;

import com.hoffmann.githubrepositories.apivalidation.dto.UserValidationErrorResponseDto;
import com.hoffmann.githubrepositories.infrastructure.controller.GitHubRepoRestController;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = GitHubRepoRestController.class)
@Log4j2
public class UserValidationErrorHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public UserValidationErrorResponseDto handleUserValidationException(UserNotFoundException exception) {
        String message = getErrorsFromException(exception);
        return new UserValidationErrorResponseDto(message,HttpStatus.NOT_FOUND);
    }

    private String getErrorsFromException(UserNotFoundException exception) {
        return exception.getMessage();
    }
}