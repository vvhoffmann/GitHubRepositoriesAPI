package com.hoffmann.githubrepositories.infrastructure.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record GetAllReposByUserRequestDto(

        @NotNull(message = "username must not be null")
        @NotEmpty(message = "username must not be empty")
        String username
) {
}
