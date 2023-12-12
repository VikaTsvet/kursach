package com.example.kursach.models;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "user_course")
@Builder
public class UsersCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduser_course")
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "course_id")
    private int courseId;

    public UsersCourse() {
    }

    public UsersCourse(int id, int userId, int courseId) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
    }

    public UsersCourse(int userId, int courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user_id) {
        this.userId = user_id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int course_id) {
        this.courseId = course_id;
    }
}
