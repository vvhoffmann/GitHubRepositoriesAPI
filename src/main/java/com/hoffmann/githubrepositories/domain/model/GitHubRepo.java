package com.hoffmann.githubrepositories.domain.model;

import java.util.List;

public record GitHubRepo(String name, String owner, List<Branch> branches) {
}
