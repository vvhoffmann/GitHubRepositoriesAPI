package com.hoffmann.githubrepositories.domain.model;

import java.util.List;

public record GitHubResult ( String owner, String name, List<Branch> branches){
}