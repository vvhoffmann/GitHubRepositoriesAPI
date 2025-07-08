package com.hoffmann.githubrepositories.domain.githubapiproxy;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Log4j2
public class GitHubServerProxy {

    RestTemplate restTemplate;

    @Value("${github-api.url}")
    String url;

    public GitHubServerProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String makeGetRequest(String username) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host(url)
                .path("/users/" + username + "/repos");

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.build().toUri(),
                    HttpMethod.GET,
                    null,
                    String.class
            );
            return response.getBody();
        } catch (IllegalArgumentException exception) {
            log.error("Username " + username + " does not exist");
        }
        return null;
    }
}