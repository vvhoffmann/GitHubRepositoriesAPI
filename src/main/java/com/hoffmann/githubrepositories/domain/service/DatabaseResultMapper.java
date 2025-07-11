package com.hoffmann.githubrepositories.domain.service;

import com.hoffmann.githubrepositories.domain.model.DatabaseResult;
import com.hoffmann.githubrepositories.domain.model.GitHubResult;
import com.hoffmann.githubrepositories.domain.model.Owner;
import com.hoffmann.githubrepositories.infrastructure.controller.dto.request.CreateDatabaseResultRequestDto;
import com.hoffmann.githubrepositories.infrastructure.controller.dto.request.UpdateDatabaseResultByIdRequestDto;
import com.hoffmann.githubrepositories.infrastructure.controller.dto.response.*;
import com.hoffmann.githubrepositories.infrastructure.controller.dto.request.GetAllReposByUserRequestDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class DatabaseResultMapper {

    public static Owner mapFromGetAllGithubReposByUserRequestDtoToOwner(GetAllReposByUserRequestDto request) {
        return new Owner(request.username());
    }

    public static GetAllResultResponseDto mapFromGitHubResultListToGetAllGithubReposResponseDto(
            List<GitHubResult> repos) {
        return new GetAllResultResponseDto(repos);
    }

    public static GetAllDatabaseResultResponseDto mapFromDatabaseResultListToGetAllDatabaseGithubReposResponseDto(
            List<DatabaseResult> repos) {
        List<ResultDto> dtoList = repos.stream()
                .map(DatabaseResultMapper::mapFromDatabaseResultToResultDto)
                .collect(Collectors.toList());
        return new GetAllDatabaseResultResponseDto(dtoList);
    }

    private static ResultDto mapFromDatabaseResultToResultDto(DatabaseResult result) {
        return new ResultDto(result.getId(), result.getOwner(), result.getName());
    }

    public static DatabaseResult mapFromGitHubResultToDatabaseResult(GitHubResult result) {
        return new DatabaseResult(result.owner(), result.name());
    }

    public static DatabaseResult mapFromCreateDatabaseResultRequestDtoToDatabaseResult(
            CreateDatabaseResultRequestDto request) {
        return new DatabaseResult(request.owner(), request.name());
    }

    public static DeleteDatabaseResultByIdResponseDto mapFromResultIdToDeleteSongResponseDto(Long id) {
        return new DeleteDatabaseResultByIdResponseDto("You deleted database result with id " + id, HttpStatus.OK);
    }

    public static DatabaseResult mapUpdateDatabaseResultByIdRequestDtoToDatabaseResult(
            UpdateDatabaseResultByIdRequestDto request) {
        return new DatabaseResult(request.owner(), request.name());
    }

    public static UpdateDatabaseResultByIdResponseDto mapFromDatabaseResultToUpdateDatabaseResultByIdRequestDto(
            DatabaseResult resultToUpdate) {
        return new UpdateDatabaseResultByIdResponseDto(
                new ResultDto(resultToUpdate.getId(), resultToUpdate.getOwner(), resultToUpdate.getName()
                )
        );
    }

    public CreateDatabaseResultResponseDto mapFromDatabaseResultToCreateDatabaseResultRequestDto(
            DatabaseResult savedResult) {
        return new CreateDatabaseResultResponseDto(
                new ResultDto(savedResult.getId(), savedResult.getOwner(), savedResult.getName()
                )
        );
    }
}