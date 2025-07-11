package com.hoffmann.githubrepositories;

import com.hoffmann.githubrepositories.domain.service.proxy.GitHubProxyResultService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MainApplicationRunner {

    GitHubProxyResultService gitHubProxyResultService;

    public MainApplicationRunner(GitHubProxyResultService gitHubProxyResultService) {
        this.gitHubProxyResultService = gitHubProxyResultService;
    }

    public void run() {
        log.info("Starting application");
    }
}