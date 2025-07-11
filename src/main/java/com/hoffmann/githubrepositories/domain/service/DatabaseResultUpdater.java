package com.hoffmann.githubrepositories.domain.service;

import com.hoffmann.githubrepositories.domain.model.DatabaseResult;
import com.hoffmann.githubrepositories.domain.repository.ResultRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@Transactional
public class DatabaseResultUpdater {

    private final ResultRepository resultRepository;
    private final DatabaseResultRetriever databaseResultRetriever;

    public DatabaseResultUpdater(ResultRepository resultRepository, DatabaseResultRetriever databaseResultRetriever) {
        this.resultRepository = resultRepository;
        this.databaseResultRetriever = databaseResultRetriever;
    }

    public void updateById(Long id, DatabaseResult resultToUpdate) {
        databaseResultRetriever.existsById(id);
        resultRepository.updateById(id,resultToUpdate);
        log.info("Updating result with id " + id + " to " + resultToUpdate);
    }
}