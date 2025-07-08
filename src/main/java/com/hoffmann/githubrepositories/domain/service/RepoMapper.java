package com.hoffmann.githubrepositories.domain.service;

import com.hoffmann.githubrepositories.domain.model.GitHubRepo;
import com.hoffmann.githubrepositories.domain.model.Owner;
import com.hoffmann.githubrepositories.infrastructure.controller.dto.request.GetAllGithubReposRequestDto;
import com.hoffmann.githubrepositories.infrastructure.controller.dto.response.GetAllGithubReposResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class RepoMapper {

    public static Owner mapFromGetAllGithubReposRequestDtoToOwner(GetAllGithubReposRequestDto request) {
        return new Owner(request.username());
    }

    public static GetAllGithubReposResponseDto mapFromOwnerToGetAllGithubReposResponseDto(List<GitHubRepo> repos) {
        return new GetAllGithubReposResponseDto(repos);
    }
}