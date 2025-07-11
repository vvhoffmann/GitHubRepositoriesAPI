package com.hoffmann.githubrepositories.infrastructure.controller.error;

import org.springframework.http.HttpStatus;

public class ResultNotFindException extends RuntimeException {
    public ResultNotFindException(String s) {
        super(s);
    }

    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}