package com.hoffmann.githubrepositories.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name="repo")
public class DatabaseResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private String name;

    public DatabaseResult(Long id, String owner, String name) {
        this.id = id;
        this.owner = owner;
        this.name = name;
    }

    public DatabaseResult(String owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    public DatabaseResult() {
    }
}