package com.hoffmann.githubrepositories.domain.service;

import com.hoffmann.githubrepositories.UserNotFoundException;
import com.hoffmann.githubrepositories.domain.githubapiproxy.GitHubServerProxy;
import com.hoffmann.githubrepositories.domain.model.GitHubRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class GitHubRepoService {

    GitHubServerProxy gitHubServerProxy;
    GitHubRepoMapper gitHubRepoMapper;

    public GitHubRepoService(GitHubServerProxy gitHubServerProxy, GitHubRepoMapper gitHubRepoMapper) {
        this.gitHubServerProxy = gitHubServerProxy;
        this.gitHubRepoMapper = gitHubRepoMapper;
    }

    public List<GitHubRepo> fetchAllRepos(String username)
    {
        try {
            String jsonResponse = gitHubServerProxy.makeGetRequest(username);
            return gitHubRepoMapper.mapJsonToGitHubRepoListResponseDto(jsonResponse)
                    .stream()
                    .filter(gitHubRepo -> !gitHubRepo.fork())
                    .toList();
        } catch (HttpClientErrorException exception) {
            throw new UserNotFoundException("This username doesn't exist");
        }
    }
}
