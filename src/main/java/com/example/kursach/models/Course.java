package com.example.kursach.models;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "courses")
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcourses")
    private int id;
    private String name;
    private String language;
    private String level;
    private int quantity_of_students;
    private int age_of_group;
    private double price;
    private String description;

    public Course() {
    }

    public Course(int id, String name, String language, String level, int quantity_of_students, int age_of_group, double price, String description) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.level = level;
        this.quantity_of_students = quantity_of_students;
        this.age_of_group = age_of_group;
        this.price = price;
        this.description = description;
    }

    public Course(String name, String language, String level, int quantity_of_students, int age_of_group, double price, String description) {
        this.name = name;
        this.language = language;
        this.level = level;
        this.quantity_of_students = quantity_of_students;
        this.age_of_group = age_of_group;
        this.price = price;
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

    public int getQuantity_of_students() {
        return quantity_of_students;
    }

    public void setQuantity_of_students(int quantity_of_students) {
        this.quantity_of_students = quantity_of_students;
    }

    public int getAge_of_group() {
        return age_of_group;
    }

    public void setAge_of_group(int age_of_group) {
        this.age_of_group = age_of_group;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
