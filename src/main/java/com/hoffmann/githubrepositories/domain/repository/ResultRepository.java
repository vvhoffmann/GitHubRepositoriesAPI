package com.hoffmann.githubrepositories.domain.repository;

import com.hoffmann.githubrepositories.domain.model.DatabaseResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.web.PageableDefault;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends Repository<DatabaseResult, Long> {

    DatabaseResult save(DatabaseResult result);

    @Query("SELECT r FROM DatabaseResult r")
    List<DatabaseResult> findAll(@PageableDefault(page = 0 , size = 12) Pageable pageable);

    @Query("SELECT r FROM DatabaseResult r WHERE r.id = :id")
    Optional<DatabaseResult> findById(Long id);

    @Modifying
    @Query("DELETE FROM DatabaseResult r WHERE r.id = :id")
    void deleteById(Long id);

    @Modifying
    @Query("UPDATE DatabaseResult r SET r.owner = :#{#newRepo.owner}, r.name = :#{#newRepo.name} WHERE r.id = :id")
    void updateById(Long id, DatabaseResult newRepo);
}