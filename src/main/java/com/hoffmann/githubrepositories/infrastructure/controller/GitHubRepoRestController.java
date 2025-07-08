package com.hoffmann.githubrepositories.infrastructure.controller;

import com.hoffmann.githubrepositories.domain.model.GitHubRepo;
import com.hoffmann.githubrepositories.domain.model.Owner;
import com.hoffmann.githubrepositories.domain.service.RepoMapper;
import com.hoffmann.githubrepositories.domain.service.RepoProxyService;
import com.hoffmann.githubrepositories.infrastructure.controller.dto.request.GetAllGithubReposRequestDto;
import com.hoffmann.githubrepositories.infrastructure.controller.dto.response.GetAllGithubReposResponseDto;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/repositories")
public class GitHubRepoRestController {

    RepoProxyService repoProxyService;

    public GitHubRepoRestController(RepoProxyService repoProxyService) {
        this.repoProxyService = repoProxyService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<GetAllGithubReposResponseDto> getAllRepositories(
            @PathVariable("username") @Valid GetAllGithubReposRequestDto username
    ) {
        Owner owner = RepoMapper.mapFromGetAllGithubReposRequestDtoToOwner(username);
        List<GitHubRepo> repos = repoProxyService.fetchAllInfo(owner.name());
        GetAllGithubReposResponseDto body = RepoMapper.mapFromOwnerToGetAllGithubReposResponseDto(repos);

        return ResponseEntity.ok(body);
    }
}