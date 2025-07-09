package com.hoffmann.githubrepositories.domain.service;

import com.hoffmann.githubrepositories.apivalidation.ApiLimitExceededException;
import com.hoffmann.githubrepositories.apivalidation.UserNotFoundException;
import com.hoffmann.githubrepositories.domain.apiproxy.GitHubAPIProxy;
import com.hoffmann.githubrepositories.domain.apiproxy.dto.BranchDto;
import com.hoffmann.githubrepositories.domain.apiproxy.dto.RepoDto;
import com.hoffmann.githubrepositories.domain.model.GitHubRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubProxyService {

    GitHubAPIProxy gitHubAPIProxy;
    GitHubProxyMapper gitHubProxyMapper;

    public GitHubProxyService(GitHubAPIProxy gitHubAPIProxy, GitHubProxyMapper gitHubProxyMapper) {
        this.gitHubAPIProxy = gitHubAPIProxy;
        this.gitHubProxyMapper = gitHubProxyMapper;
    }

    public List<GitHubRepo> fetchAllInfo(String owner) {
        List<GitHubRepo> gitHubRepoFinalList = new ArrayList<>();
        List<RepoDto> gitHubRepoList = fetchAllRepos(owner);
        for (RepoDto repo : gitHubRepoList) {
            List<BranchDto> branchesDto = fetchAllBranches(owner, repo.name());
            GitHubRepo gitHubRepo = gitHubProxyMapper.mapGitHubRepoAndBranchesListDtoToGitHubRepo(repo, branchesDto);
            gitHubRepoFinalList.add(gitHubRepo);
        }
        return gitHubRepoFinalList;
    }

    private List<RepoDto> fetchAllRepos(String username) {
        try {
            String jsonResponse = gitHubAPIProxy.makeGetReposRequest(username);
            return gitHubProxyMapper.mapJsonToGitHubRepoListResponseDto(jsonResponse)
                    .stream()
                    .filter(repo -> !repo.fork())
                    .toList();
        } catch (HttpClientErrorException.Forbidden exception) {
            throw new ApiLimitExceededException("Api limit exceeded");
        } catch (HttpClientErrorException exception) {
            throw new UserNotFoundException("This username doesn't exist");
        }
    }

    private List<BranchDto> fetchAllBranches(String owner, String repoName) {
        try {
            String jsonResponse = gitHubAPIProxy.makeGetBranchByRepoRequest(owner, repoName);
            return gitHubProxyMapper.mapJsonToGitHubRepoWithBranchesListResponseDto(jsonResponse);
        } catch (HttpClientErrorException exception) {
            throw new UserNotFoundException("This username doesn't exist");
        }
    }
}