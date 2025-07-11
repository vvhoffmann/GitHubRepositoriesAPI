package com.hoffmann.githubrepositories.domain.service.proxy;

import com.hoffmann.githubrepositories.apivalidation.exceptions.ApiLimitExceededException;
import com.hoffmann.githubrepositories.apivalidation.exceptions.UserNotFoundException;
import com.hoffmann.githubrepositories.infrastructure.controller.apiproxy.GitHubAPIProxy;
import com.hoffmann.githubrepositories.infrastructure.controller.apiproxy.dto.BranchDto;
import com.hoffmann.githubrepositories.infrastructure.controller.apiproxy.dto.RepoDto;
import com.hoffmann.githubrepositories.domain.model.GitHubResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubProxyResultService {

    GitHubAPIProxy gitHubAPIProxy;
    GitHubProxyResultMapper gitHubProxyResultMapper;

    public GitHubProxyResultService(GitHubAPIProxy gitHubAPIProxy, GitHubProxyResultMapper gitHubProxyResultMapper) {
        this.gitHubAPIProxy = gitHubAPIProxy;
        this.gitHubProxyResultMapper = gitHubProxyResultMapper;
    }

    public List<GitHubResult> fetchAllInfo(String owner) {
        List<GitHubResult> gitHubResultFinalList = new ArrayList<>();
        List<RepoDto> repoList = fetchAllRepos(owner);
        for (RepoDto repo : repoList) {
            List<BranchDto> branchesDto = fetchAllBranches(owner, repo.name());
            GitHubResult gitHubDatabaseResult = gitHubProxyResultMapper.mapGitHubRepoAndBranchesListDtoToGitHubResult(repo, branchesDto);
            gitHubResultFinalList.add(gitHubDatabaseResult);
        }
        return gitHubResultFinalList;
    }

    private List<RepoDto> fetchAllRepos(String username) {
        try {
            String jsonResponse = gitHubAPIProxy.makeGetReposRequest(username);
            return gitHubProxyResultMapper.mapJsonToGitHubRepoListResponseDto(jsonResponse)
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
            return gitHubProxyResultMapper.mapJsonToGitHubRepoWithBranchesListResponseDto(jsonResponse);
        } catch (HttpClientErrorException exception) {
            throw new UserNotFoundException("This username doesn't exist");
        }
    }
}