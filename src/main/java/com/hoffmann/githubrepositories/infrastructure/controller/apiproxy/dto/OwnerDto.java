package com.hoffmann.githubrepositories.infrastructure.controller.apiproxy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OwnerDto(String login) {
}
