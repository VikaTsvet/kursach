package com.example.kursach.repositories;

import com.example.kursach.models.UsersCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersCourseRepository extends JpaRepository<UsersCourse, Integer> {
    List<UsersCourse> findByUserId(int userId);
    boolean existsByCourseIdAndUserId(int courseId, int userId);
    UsersCourse findByUserIdAndCourseId(int userId, int courseId);
}
