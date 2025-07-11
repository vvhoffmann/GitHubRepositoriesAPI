package com.hoffmann.githubrepositories.infrastructure.controller.dto.response;

import org.springframework.http.HttpStatus;

public record DeleteDatabaseResultByIdResponseDto(String message, HttpStatus httpStatus) {
}
