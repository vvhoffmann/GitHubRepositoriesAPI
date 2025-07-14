package com.hoffmann.githubrepositories.domain.service.proxy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoffmann.githubrepositories.infrastructure.controller.apiproxy.dto.BranchDto;
import com.hoffmann.githubrepositories.infrastructure.controller.apiproxy.dto.RepoDto;
import com.hoffmann.githubrepositories.domain.model.Branch;
import com.hoffmann.githubrepositories.domain.model.GitHubResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class GitHubProxyResultMapper {

    private final ObjectMapper objectMapper;

    public GitHubProxyResultMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<RepoDto> mapJsonToGitHubRepoListResponseDto(String jsonResponse) {
        try {
            return objectMapper.readValue(
                    jsonResponse,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, RepoDto.class)
            );
        } catch (JsonProcessingException exception) {
            log.error("GitHubRepoMapper couldn't map json: " + exception.getMessage());
            return Collections.emptyList();
        }
    }

    public List<BranchDto> mapJsonToListBranchesDto(String jsonResponse) {
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

    public GitHubResult mapGitHubRepoAndBranchesListDtoToGitHubResult(RepoDto repo, List<BranchDto> branchesDto)
    {
        List<Branch> branches = mapBranchesDtoListToBranchesList(branchesDto);
        return new GitHubResult(repo.owner().login(), repo.name(), branches);
    }

    private List<Branch> mapBranchesDtoListToBranchesList(List<BranchDto> branches)
    {
        List<Branch> branchesFinalList = new ArrayList<>();
        for (BranchDto branchDto : branches)
            branchesFinalList.add(new Branch(branchDto.name(), branchDto.commit().sha()));

        return branchesFinalList;
    }
}