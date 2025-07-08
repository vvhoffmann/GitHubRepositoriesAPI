package com.hoffmann.githubrepositories.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoffmann.githubrepositories.domain.githubapiproxy.dto.BranchDto;
import com.hoffmann.githubrepositories.domain.githubapiproxy.dto.GitHubRepoDto;
import com.hoffmann.githubrepositories.domain.model.Branch;
import com.hoffmann.githubrepositories.domain.model.GitHubRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class GitHubRepoMapper {

    private final ObjectMapper objectMapper;

    public GitHubRepoMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<GitHubRepoDto> mapJsonToGitHubRepoListResponseDto(String jsonResponse) {
        try {
            return objectMapper.readValue(
                    jsonResponse,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, GitHubRepoDto.class)
            );
        } catch (JsonProcessingException exception) {
            log.error("GitHubRepoMapper couldn't map json: " + exception.getMessage());
            return Collections.emptyList();
        }
    }

    public List<BranchDto> mapJsonToGitHubRepoWithBranchesListResponseDto(String jsonResponse) {
        try {
            return objectMapper.readValue(
                    jsonResponse,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, BranchDto.class)
            );
        } catch (JsonProcessingException exception) {
            log.error("GitHubRepoMapper couldn't map json: " + exception.getMessage());
            return Collections.emptyList();
        }
    }

    public GitHubRepo mapGitHubRepoAndBranchesListDtoToGitHubRepo(GitHubRepoDto repo, List<BranchDto> branchesDto)
    {
        List<Branch> branches = mapBranchesDtoListToBranchesList(branchesDto);
        return new GitHubRepo(repo.name(), repo.owner().login(), branches);
    }

    private List<Branch> mapBranchesDtoListToBranchesList(List<BranchDto> branches)
    {
        List<Branch> branchesFinalList = new ArrayList<>();
        for (BranchDto branchDto : branches)
            branchesFinalList.add(new Branch(branchDto.name(), branchDto.commit().sha()));

        return branchesFinalList;
    }
}