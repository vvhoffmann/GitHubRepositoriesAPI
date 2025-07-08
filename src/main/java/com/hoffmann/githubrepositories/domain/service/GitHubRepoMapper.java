package com.hoffmann.githubrepositories.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoffmann.githubrepositories.domain.model.GitHubRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class GitHubRepoMapper {

    private final ObjectMapper objectMapper;

    public GitHubRepoMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<GitHubRepo> mapJsonToGitHubRepoListResponseDto(String jsonResponse) {
        try {
            return objectMapper.readValue(
                    jsonResponse,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, GitHubRepo.class)
            );
        } catch (JsonProcessingException exception) {
            log.error("GitHubRepoMapper couldn't map json: " + exception.getMessage());
            return Collections.emptyList();
        }
    }
}
