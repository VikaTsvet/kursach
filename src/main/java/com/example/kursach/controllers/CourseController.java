package com.example.kursach.controllers;

import com.example.kursach.models.Course;
import com.example.kursach.models.User;
import com.example.kursach.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    public String allCourses(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("title", "Курсы");
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        model.addAttribute("user", user);
        return "courses";
    }

    @PostMapping("/courses")
    public String searchAndSortCourse(@AuthenticationPrincipal User user, @RequestParam String searchBy,
                                      @RequestParam String value, @RequestParam String sortBy, Model model) {
        model.addAttribute("title", "Курсы");
        if (!value.isEmpty()) {
            List<Course> courses = courseService.searchCourseBy(searchBy, value);
            model.addAttribute("courses", courses);
        } else {
            List<Course> allCourses = courseService.findAll();
            List<Course> courses = courseService.sortCoursesBy(sortBy, allCourses);
            model.addAttribute("courses", courses);
        }
        model.addAttribute("user", user);
        return "courses";
    }

    @GetMapping("/courses/add")
    public String addCourse(Model model) {
        return "courses-add";
    }

    @PostMapping("/courses/add")
    public String addCoursePost(@RequestParam String name, @RequestParam String language, @RequestParam String level,
                                @RequestParam int quantity_of_students, @RequestParam int age_of_group,
                                @RequestParam double price, @RequestParam String description, Model model) {
        Course course = new Course(name, language, level, quantity_of_students, age_of_group, price, description);
        courseService.addCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("/courses/{id}")
    public String courseInfo(@AuthenticationPrincipal User user, @PathVariable(value = "id") int id, Model model) {
        if (!courseService.existsById(id)) {
            return "redirect:/courses";
        }
        Course result = courseService.findById(id);
        model.addAttribute("course", result);
        model.addAttribute("user", user);
        return "course-info";
    }

    @GetMapping("/courses/{id}/edit")
    public String editCourse(@PathVariable(value = "id") int id, Model model) {
        if (!courseService.existsById(id)) {
            return "redirect:/courses";
        }
        Course result = courseService.findById(id);
        model.addAttribute("course", result);
        return "course-edit";
    }

    @PostMapping("/courses/{id}/edit")
    public String editCoursePost(@PathVariable(value = "id") int id, @RequestParam String name, @RequestParam String language,
                                 @RequestParam String level, @RequestParam int quantity_of_students,
                                 @RequestParam int age_of_group, @RequestParam double price, @RequestParam String description,
                                 Model model) {
        courseService.editCourse(id, name, language, level, quantity_of_students, age_of_group, price, description);
        return "redirect:/courses";
    }

    @PostMapping("/courses/{id}/delete")
    public String deleteCoursePost(@PathVariable(value = "id") int id, Model model) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }

    @PostMapping("/courses/{id}/buy")
    public String buyCourse(@AuthenticationPrincipal User user, @PathVariable(value = "id") int id, Model model) {
        model.addAttribute("title", "Курсы");
        courseService.buyCourse(user.getId(), id);
        return "redirect:/courses";
    }
}
