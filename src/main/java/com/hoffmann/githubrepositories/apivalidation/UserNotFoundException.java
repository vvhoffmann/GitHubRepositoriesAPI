package com.hoffmann.githubrepositories.apivalidation;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
