package com.hoffmann.githubrepositories;

import com.hoffmann.githubrepositories.domain.service.RepoProxyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MainApplicationRunner {

    RepoProxyService repoProxyService;

    public MainApplicationRunner(RepoProxyService repoProxyService) {
        this.repoProxyService = repoProxyService;
    }

    public void run() {
        log.info("Starting application");
    }
}