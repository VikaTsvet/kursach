package com.example.kursach.models;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "books")
@Builder
public class Book {
    @Id//для указания первичного ключа сущности
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idbooks")
    private int id;
    private String name;
    private String author;
    private String language;
    private String level;
    private String description;

    public Book() {
    }

    public Book(int id, String name, String author, String language, String level, String description) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.language = language;
        this.level = level;
        this.description = description;
    }

    public Book(String name, String author, String language, String level, String description) {
        this.name = name;
        this.author = author;
        this.language = language;
        this.level = level;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
