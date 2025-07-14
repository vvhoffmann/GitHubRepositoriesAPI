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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class GitHubProxyResultService {

    GitHubAPIProxy gitHubAPIProxy;
    GitHubProxyResultMapper gitHubProxyResultMapper;

    public GitHubProxyResultService(GitHubAPIProxy gitHubAPIProxy, GitHubProxyResultMapper gitHubProxyResultMapper) {
        this.gitHubAPIProxy = gitHubAPIProxy;
        this.gitHubProxyResultMapper = gitHubProxyResultMapper;
    }

    public List<GitHubResult> fetchAllInfo(String owner) {
        List<RepoDto> repoNames = fetchAllRepos(owner);

        ExecutorService executorService = Executors.newFixedThreadPool(repoNames.size());
        List<Future<GitHubResult>> futures = new ArrayList<>();
        for (RepoDto repo : repoNames) {
            futures.add(executorService.submit(() -> getGitHubResult(owner, repo)));
        }

        List<GitHubResult> gitHubResultFinalList = new ArrayList<>();
        for(Future<GitHubResult> future : futures) {
            try {
                GitHubResult gitHubResult = future.get();
                gitHubResultFinalList.add(gitHubResult);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        executorService.shutdown();
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

    private GitHubResult getGitHubResult(String owner, RepoDto repo) {
        try {
            List<BranchDto> branchesDto = fetchAllBranches(owner, repo);
            return gitHubProxyResultMapper.mapGitHubRepoAndBranchesListDtoToGitHubResult(repo, branchesDto);
        } catch (HttpClientErrorException exception) {
            throw new UserNotFoundException("This username doesn't exist");
        }
    }

    private List<BranchDto> fetchAllBranches(String owner, RepoDto repo) {
        String jsonResponse = gitHubAPIProxy.makeGetBranchesByRepoRequest(owner, repo.name());
        return gitHubProxyResultMapper.mapJsonToListBranchesDto(jsonResponse);
    }
}