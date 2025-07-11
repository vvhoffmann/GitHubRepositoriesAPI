package com.hoffmann.githubrepositories.domain.service;

import com.hoffmann.githubrepositories.domain.repository.ResultRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Log4j2
public class DatabaseResultDeleter {


    private final ResultRepository resultRepository;
    private final DatabaseResultRetriever databaseResultRetriever;

    public DatabaseResultDeleter(ResultRepository resultRepository, DatabaseResultRetriever databaseResultRetriever) {
        this.resultRepository = resultRepository;
        this.databaseResultRetriever = databaseResultRetriever;
    }

    public void deleteById(Long id) {
        databaseResultRetriever.existsById(id);
        log.info("Deleting repo with id: {}", id);
        resultRepository.deleteById(id);
    }
}