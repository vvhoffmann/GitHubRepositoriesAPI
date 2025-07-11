package com.hoffmann.githubrepositories.infrastructure.controller;

import com.hoffmann.githubrepositories.domain.model.DatabaseResult;
import com.hoffmann.githubrepositories.domain.model.GitHubResult;
import com.hoffmann.githubrepositories.domain.model.Owner;
import com.hoffmann.githubrepositories.domain.service.*;
import com.hoffmann.githubrepositories.domain.service.proxy.GitHubProxyResultService;
import com.hoffmann.githubrepositories.infrastructure.controller.dto.request.CreateDatabaseResultRequestDto;
import com.hoffmann.githubrepositories.infrastructure.controller.dto.request.GetAllReposByUserRequestDto;
import com.hoffmann.githubrepositories.infrastructure.controller.dto.request.UpdateDatabaseResultByIdRequestDto;
import com.hoffmann.githubrepositories.infrastructure.controller.dto.response.*;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/repositories")
public class GitHubRepositoriesRestController {

    private final DatabaseResultRetriever databaseResultRetriever;
    private final GitHubProxyResultService gitHubProxyResultService;
    private final DatabaseResultAdder databaseResultAdder;
    private final DatabaseResultDeleter databaseResultDeleter;
    private final DatabaseResultUpdater databaseResultUpdater;
    private final DatabaseResultMapper databaseResultMapper;

    public GitHubRepositoriesRestController(GitHubProxyResultService gitHubProxyResultService,
                                            DatabaseResultAdder databaseResultAdder,
                                            DatabaseResultRetriever databaseResultRetriever, DatabaseResultDeleter databaseResultDeleter, DatabaseResultUpdater databaseResultUpdater, DatabaseResultMapper databaseResultMapper) {
        this.gitHubProxyResultService = gitHubProxyResultService;
        this.databaseResultAdder = databaseResultAdder;
        this.databaseResultRetriever = databaseResultRetriever;
        this.databaseResultDeleter = databaseResultDeleter;
        this.databaseResultUpdater = databaseResultUpdater;
        this.databaseResultMapper = databaseResultMapper;
    }

    @GetMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetAllResultResponseDto> getAllRepositoriesWithBranches(
            @PathVariable("username") @Valid GetAllReposByUserRequestDto username
    ) {
        Owner owner = DatabaseResultMapper.mapFromGetAllGithubReposByUserRequestDtoToOwner(username);
        List<GitHubResult> repos = gitHubProxyResultService.fetchAllInfo(owner.name());
        databaseResultAdder.saveAll(repos);
        GetAllResultResponseDto body = DatabaseResultMapper
                .mapFromGitHubResultListToGetAllGithubReposResponseDto(repos);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/database")
    public ResponseEntity<GetAllDatabaseResultResponseDto> getAllRepositoriesFromDb(Pageable pageable) {
        List<DatabaseResult> repos = databaseResultRetriever.fetchAllInfo(pageable);
        GetAllDatabaseResultResponseDto body = DatabaseResultMapper
                .mapFromDatabaseResultListToGetAllDatabaseGithubReposResponseDto(repos);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/database")
    public ResponseEntity<CreateDatabaseResultResponseDto> createGitHubRepository(
            @RequestBody @Valid CreateDatabaseResultRequestDto request)
    {
        DatabaseResult resultToSave = DatabaseResultMapper.mapFromCreateDatabaseResultRequestDtoToDatabaseResult(request);
        databaseResultAdder.save(resultToSave);
        CreateDatabaseResultResponseDto body = databaseResultMapper.mapFromDatabaseResultToCreateDatabaseResultRequestDto(resultToSave);
        return ResponseEntity.ok(body);
    }


    @DeleteMapping("database/{id}")
    public ResponseEntity<DeleteDatabaseResultByIdResponseDto> deleteDatabaseResult(@PathVariable Long id)
    {
        databaseResultDeleter.deleteById(id);
        DeleteDatabaseResultByIdResponseDto body = DatabaseResultMapper.mapFromResultIdToDeleteSongResponseDto(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping("database/{id}")
    public ResponseEntity<UpdateDatabaseResultByIdResponseDto> updateDatabaseResult(
            @PathVariable Long id,
            @RequestBody @Valid UpdateDatabaseResultByIdRequestDto request)
    {
        DatabaseResult resultToUpdate = DatabaseResultMapper.mapUpdateDatabaseResultByIdRequestDtoToDatabaseResult(request);
        databaseResultUpdater.updateById(id, resultToUpdate);
        UpdateDatabaseResultByIdResponseDto updateSongResponseDto = DatabaseResultMapper.mapFromDatabaseResultToUpdateDatabaseResultByIdRequestDto(resultToUpdate);
        return ResponseEntity.ok(updateSongResponseDto);
    }

}