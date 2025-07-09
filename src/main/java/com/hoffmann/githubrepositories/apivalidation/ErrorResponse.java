package com.hoffmann.githubrepositories.apivalidation;

import org.springframework.http.HttpStatusCode;

public record ErrorResponse(HttpStatusCode status, String message){
}