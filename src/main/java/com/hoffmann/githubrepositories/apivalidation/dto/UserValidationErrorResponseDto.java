package com.hoffmann.githubrepositories.apivalidation.dto;

import org.springframework.http.HttpStatus;


public record UserValidationErrorResponseDto(String message, HttpStatus httpStatus) {
}
