package com.hoffmann.githubrepositories;

import com.hoffmann.githubrepositories.domain.service.GitHubProxyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MainApplicationRunner {

    GitHubProxyService gitHubProxyService;

    public MainApplicationRunner(GitHubProxyService gitHubProxyService) {
        this.gitHubProxyService = gitHubProxyService;
    }

    public void run() {
        log.info("Starting application");
    }
}