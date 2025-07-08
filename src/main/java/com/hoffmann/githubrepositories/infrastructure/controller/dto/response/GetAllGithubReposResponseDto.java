package com.hoffmann.githubrepositories.infrastructure.controller.dto.response;

import com.hoffmann.githubrepositories.domain.model.GitHubRepo;

import java.util.List;

public record GetAllGithubReposResponseDto(List<GitHubRepo> repositories) {
}