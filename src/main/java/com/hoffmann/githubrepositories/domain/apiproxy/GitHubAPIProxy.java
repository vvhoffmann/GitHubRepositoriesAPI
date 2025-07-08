package com.hoffmann.githubrepositories.domain.apiproxy;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Log4j2
public class GitHubAPIProxy {

    RestTemplate restTemplate;

    @Value("${github-api.url}")
    String url;

    public GitHubAPIProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String makeGetReposRequest(String owner) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host(url)
                .path("/users/" + owner + "/repos");
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.build().toUri(),
                    HttpMethod.GET,
                    null,
                    String.class
            );
            return response.getBody();
        } catch (IllegalArgumentException exception) {
            log.error("Username " + owner + " does not exist");
        }
        return null;
    }

    public String makeGetBranchByRepoRequest(String owner, String repo) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host(url)
                .path("/repos/" + owner + "/" + repo + "/branches");
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.build().toUri(),
                    HttpMethod.GET,
                    null,
                    String.class
            );
            return response.getBody();
        } catch (RuntimeException exception) {
            log.error("Fetching branches from " + repo + " failed");
        }
        return null;
    }
}