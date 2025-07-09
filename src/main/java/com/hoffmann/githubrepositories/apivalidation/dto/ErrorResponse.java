package com.hoffmann.githubrepositories.apivalidation.dto;

import org.springframework.http.HttpStatusCode;

public record ErrorResponse(HttpStatusCode status, String message){
}