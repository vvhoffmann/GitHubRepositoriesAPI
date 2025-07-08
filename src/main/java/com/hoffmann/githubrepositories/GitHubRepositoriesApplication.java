package com.hoffmann.githubrepositories;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class GitHubRepositoriesApplication {

    MainApplicationRunner mainApplicationRunner;

    public GitHubRepositoriesApplication(MainApplicationRunner mainApplicationRunner) {
        this.mainApplicationRunner = mainApplicationRunner;
    }

    public static void main(String[] args) {
        SpringApplication.run(GitHubRepositoriesApplication.class, args);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void start()
    {
        mainApplicationRunner.run();
    }

}