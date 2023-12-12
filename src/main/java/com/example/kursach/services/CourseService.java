package com.example.kursach.services;

import com.example.kursach.models.Course;
import com.example.kursach.models.UsersCourse;
import com.example.kursach.repositories.CourseRepository;
import com.example.kursach.repositories.UsersCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UsersCourseRepository usersCourseRepository;

    public List<Course> searchCourseBy(String searchBy, String value) {
        Iterable<Course> courses = courseRepository.findAll();
        List<Course> result = new ArrayList<>();
        switch (searchBy) {
            case "По названию": {
                for (Course course : courses) {
                    if (course.getName().toLowerCase().contains(value.toLowerCase())) {
                        result.add(course);
                    }
                }
                break;
            }
            case "По языку": {
                for (Course course : courses) {
                    if (course.getLanguage().toLowerCase().contains(value.toLowerCase())) {
                        result.add(course);
                    }
                }
                break;
            }
            case "По уровню": {
                for (Course course : courses) {
                    if (course.getLevel().toLowerCase().contains(value.toLowerCase())) {
                        result.add(course);
                    }
                }
                break;
            }
        }
        return result;
    }

    public List<Course> sortCoursesBy(String sortValue, List<Course> courses) {
        switch (sortValue) {
            case "По названию": {
                Collections.sort(courses, (s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()) > 1 ? 1 : s1.getName().compareToIgnoreCase(s2.getName()) < 1 ? -1 : 0);
                break;
            }
            case "По цене": {
                Collections.sort(courses, (s1, s2) -> s1.getPrice() > s2.getPrice() ? 1 : -1);
                break;
            }
        }

        return courses;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(int id) {
        Optional<Course> course = courseRepository.findById(id);
        ArrayList<Course> result = new ArrayList<>();
        course.ifPresent(result::add);
        return result.get(0);
    }

    public void editCourse(int id, String name, String language, String level, int quantity_of_students, int age_of_group,
                           double price, String description) {
        Course course = courseRepository.findById(id).orElseThrow();
        course.setName(name);
        course.setLanguage(language);
        course.setLevel(level);
        course.setQuantity_of_students(quantity_of_students);
        course.setAge_of_group(age_of_group);
        course.setPrice(price);
        course.setDescription(description);
        courseRepository.save(course);
    }

    public void deleteCourse(int id) {
        Course course = courseRepository.findById(id).orElseThrow();
        courseRepository.delete(course);
    }

    public boolean existsById(int id) {
        return courseRepository.existsById(id);
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void buyCourse(int userId, int courseId) {
        if (!usersCourseRepository.existsByCourseIdAndUserId(courseId, userId)) {
            usersCourseRepository.save(new UsersCourse(userId, courseId));
        }
    }

    public List<Course> findAllUserCourses(int userId) {
        List<UsersCourse> usersCourses = usersCourseRepository.findByUserId(userId);
        List<Course> courses = new ArrayList<>();
        for (UsersCourse usersCourse : usersCourses) {
            courses.add(findById(usersCourse.getCourseId()));
        }
        return courses;
    }

    public void deleteUserCourse(int courseId, int userId) {
        if (usersCourseRepository.existsByCourseIdAndUserId(courseId, userId)) {
            UsersCourse usersCourse = usersCourseRepository.findByUserIdAndCourseId(userId, courseId);
            usersCourseRepository.delete(usersCourse);
        }
    }
}
