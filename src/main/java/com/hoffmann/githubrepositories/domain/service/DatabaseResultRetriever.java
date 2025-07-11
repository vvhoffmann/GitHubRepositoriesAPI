package com.hoffmann.githubrepositories.domain.service;

import com.hoffmann.githubrepositories.infrastructure.controller.error.ResultNotFindException;
import com.hoffmann.githubrepositories.domain.model.DatabaseResult;
import com.hoffmann.githubrepositories.domain.repository.ResultRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DatabaseResultRetriever {

    private final ResultRepository resultRepository;

    public DatabaseResultRetriever(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    private List<DatabaseResult> fetchAllRepos(Pageable pageable) {
        return resultRepository.findAll(pageable);
    }

    public List<DatabaseResult> fetchAllInfo(Pageable pageable) {
         return fetchAllRepos(pageable);
    }

    public void existsById(Long id) {
        resultRepository.findById(id)
                .orElseThrow(() -> new ResultNotFindException("Result with id " + id + " doesn't exist"));
    }
}