package com.hoffmann.githubrepositories.domain.service;

import com.hoffmann.githubrepositories.UserNotFoundException;
import com.hoffmann.githubrepositories.domain.githubapiproxy.GitHubServerProxy;
import com.hoffmann.githubrepositories.domain.githubapiproxy.dto.BranchDto;
import com.hoffmann.githubrepositories.domain.githubapiproxy.dto.GitHubRepoDto;
import com.hoffmann.githubrepositories.domain.model.GitHubRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubRepoService {

    GitHubServerProxy gitHubServerProxy;
    GitHubRepoMapper gitHubRepoMapper;

    public GitHubRepoService(GitHubServerProxy gitHubServerProxy, GitHubRepoMapper gitHubRepoMapper) {
        this.gitHubServerProxy = gitHubServerProxy;
        this.gitHubRepoMapper = gitHubRepoMapper;
    }

    private List<GitHubRepoDto> fetchAllRepos(String username)
    {
        try {
            String jsonResponse = gitHubServerProxy.makeGetReposRequest(username);
            return gitHubRepoMapper.mapJsonToGitHubRepoListResponseDto(jsonResponse)
                    .stream()
                    .filter(gitHubRepoDto -> !gitHubRepoDto.fork())
                    .toList();
        } catch (HttpClientErrorException exception) {
            throw new UserNotFoundException("This username doesn't exist");
        }
    }

    private List<BranchDto> fetchAllBranches(String owner, String repoName)
    {
        try {
            String jsonResponse = gitHubServerProxy.makeGetBranchByRepoRequest(owner, repoName);
            return gitHubRepoMapper.mapJsonToGitHubRepoWithBranchesListResponseDto(jsonResponse);
        } catch (HttpClientErrorException exception) {
            throw new UserNotFoundException("This username doesn't exist");
        }
    }

    public List<GitHubRepo> fetchAllInfo(String owner)
    {
        List<GitHubRepo> gitHubRepoFinalList = new ArrayList<>();
        List<GitHubRepoDto> gitHubRepoList = fetchAllRepos(owner);
        for(GitHubRepoDto repo : gitHubRepoList)
        {
                List<BranchDto> branchesDto = fetchAllBranches(owner, repo.name());
                GitHubRepo gitHubRepo = gitHubRepoMapper.mapGitHubRepoAndBranchesListDtoToGitHubRepo(repo, branchesDto);
                gitHubRepoFinalList.add(gitHubRepo);
        }
        return gitHubRepoFinalList;
    }
}
