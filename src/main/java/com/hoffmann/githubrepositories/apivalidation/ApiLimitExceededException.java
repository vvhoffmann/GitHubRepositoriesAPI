package com.hoffmann.githubrepositories.apivalidation;

public class ApiLimitExceededException extends RuntimeException {
    public ApiLimitExceededException(String apiLimitExceeded) {
        super(apiLimitExceeded);
    }
}
