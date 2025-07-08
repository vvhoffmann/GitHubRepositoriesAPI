package com.hoffmann.githubrepositories.domain.githubapiproxy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BranchDto (String name, CommitDto commit) {
}