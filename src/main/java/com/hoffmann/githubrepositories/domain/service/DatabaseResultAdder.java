package com.hoffmann.githubrepositories.domain.service;

import com.hoffmann.githubrepositories.domain.model.DatabaseResult;
import com.hoffmann.githubrepositories.domain.model.GitHubResult;
import com.hoffmann.githubrepositories.domain.repository.ResultRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@Transactional
public class DatabaseResultAdder {
    private final ResultRepository resultRepository;

    public DatabaseResultAdder(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public void save(GitHubResult result) {
        DatabaseResult databaseResult = DatabaseResultMapper.mapFromGitHubResultToDatabaseResult(result);
        log.info("Saved repo to database: {}", result);
        resultRepository.save(databaseResult);
    }

    public void save(DatabaseResult result) {
        log.info("Saved repo to database: {}", result);
        resultRepository.save(result);
    }

    public void saveAll(List<GitHubResult> result) {
        result.forEach(this::save);
    }
}
