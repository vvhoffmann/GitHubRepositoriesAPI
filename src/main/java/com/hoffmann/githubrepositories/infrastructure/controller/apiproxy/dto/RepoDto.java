package com.hoffmann.githubrepositories.infrastructure.controller.apiproxy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RepoDto(String name, OwnerDto owner, boolean fork){
}