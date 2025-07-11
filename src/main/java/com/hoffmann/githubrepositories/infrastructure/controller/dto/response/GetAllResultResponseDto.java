package com.hoffmann.githubrepositories.infrastructure.controller.dto.response;

import com.hoffmann.githubrepositories.domain.model.GitHubResult;

import java.util.List;

public record GetAllResultResponseDto(List<GitHubResult> repositories) {
}