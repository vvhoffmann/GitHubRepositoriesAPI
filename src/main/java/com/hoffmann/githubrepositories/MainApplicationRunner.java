package com.hoffmann.githubrepositories;

import com.hoffmann.githubrepositories.domain.service.GitHubRepoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MainApplicationRunner {

    GitHubRepoService gitHubRepoService;

    public MainApplicationRunner(GitHubRepoService gitHubRepoService) {
        this.gitHubRepoService = gitHubRepoService;
    }

    public void run() {
        log.info("Starting application");
        log.info(gitHubRepoService.fetchAllInfo("kalqa"));
    }
}