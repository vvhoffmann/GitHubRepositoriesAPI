package com.hoffmann.githubrepositories.infrastructure.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateDatabaseResultRequestDto(

        @NotEmpty(message = "Owner username must not be empty")
        @NotNull(message = "Owner username must not be null")
        String owner,

        @NotEmpty(message = "Repository name username must not be empty")
        @NotNull(message = "Repository name username must not be null")
        String name)
{
}
