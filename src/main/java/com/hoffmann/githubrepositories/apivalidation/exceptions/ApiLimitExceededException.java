package com.hoffmann.githubrepositories.apivalidation.exceptions;

import org.springframework.http.HttpStatus;

public class ApiLimitExceededException extends RuntimeException {

    public ApiLimitExceededException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}