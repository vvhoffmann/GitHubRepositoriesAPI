package com.hoffmann.githubrepositories.domain.apiproxy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RepoDto(String name, OwnerDto owner, boolean fork){
}