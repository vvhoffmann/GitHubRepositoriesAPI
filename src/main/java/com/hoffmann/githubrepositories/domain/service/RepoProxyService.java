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
public class RepoProxyService {

    GitHubAPIProxy gitHubAPIProxy;
    RepoProxyMapper repoProxyMapper;

    public RepoProxyService(GitHubAPIProxy gitHubAPIProxy, RepoProxyMapper repoProxyMapper) {
        this.gitHubAPIProxy = gitHubAPIProxy;
        this.repoProxyMapper = repoProxyMapper;
    }

    public List<GitHubRepo> fetchAllInfo(String owner) {
        List<GitHubRepo> gitHubRepoFinalList = new ArrayList<>();
        List<RepoDto> gitHubRepoList = fetchAllRepos(owner);
        for (RepoDto repo : gitHubRepoList) {
            List<BranchDto> branchesDto = fetchAllBranches(owner, repo.name());
            GitHubRepo gitHubRepo = repoProxyMapper.mapGitHubRepoAndBranchesListDtoToGitHubRepo(repo, branchesDto);
            gitHubRepoFinalList.add(gitHubRepo);
        }
        return gitHubRepoFinalList;
    }

    private List<RepoDto> fetchAllRepos(String username) {
        try {
            String jsonResponse = gitHubAPIProxy.makeGetReposRequest(username);
            return repoProxyMapper.mapJsonToGitHubRepoListResponseDto(jsonResponse)
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
            return repoProxyMapper.mapJsonToGitHubRepoWithBranchesListResponseDto(jsonResponse);
        } catch (HttpClientErrorException exception) {
            throw new UserNotFoundException("This username doesn't exist");
        }
    }
}