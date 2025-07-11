package com.hoffmann.githubrepositories.infrastructure.controller.dto.response;

import java.util.List;

public record GetAllDatabaseResultResponseDto(List<ResultDto> repositories) {
}