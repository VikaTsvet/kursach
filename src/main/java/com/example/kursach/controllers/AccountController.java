package com.example.kursach.controllers;

import com.example.kursach.models.Course;
import com.example.kursach.models.User;
import com.example.kursach.models.UsersCourse;
import com.example.kursach.services.CourseService;
import com.example.kursach.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class AccountController {
    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @GetMapping("/account")
    public String account(@AuthenticationPrincipal User user, Model model) {
        User userDB = userService.findUserById(user.getId());
        model.addAttribute("user", userDB);
        List<Course> courses = courseService.findAllUserCourses(user.getId());
        model.addAttribute("courses", courses);
        return "account";
    }

    @GetMapping("/account/edit")
    public String accountEdit(@AuthenticationPrincipal User user, Model model) {
        User userDB = userService.findUserById(user.getId());
        model.addAttribute("user", userDB);
        return "account-edit";
    }

    @PostMapping("/account/edit")
    public String accountEditPost(@AuthenticationPrincipal User user, @RequestParam String name, @RequestParam String surname,
                                  @RequestParam int age, @RequestParam String login,
                                  @RequestParam(value = "file") MultipartFile file, Model model) throws IOException {
        userService.editUser(user.getId(), name, surname, age, login, file);
        return "redirect:/account";
    }

    @GetMapping("/account/edit/password")
    public String accountEditPassword(Model model) {
        return "account-edit-password";
    }

    @PostMapping("/account/edit/password")
    public String accountEditPasswordPost(@AuthenticationPrincipal User user, @RequestParam String lastPassword,
                                          @RequestParam String newPassword1, @RequestParam String newPassword2, Model model) {
        if(userService.editPassword(user.getId(), lastPassword, newPassword1, newPassword2)){
            return "redirect:/account";
        } else
            return "account-edit-password";
    }

    @PostMapping("/account/{id}/delete")
    public String accountDeleteCourse(@AuthenticationPrincipal User user, @PathVariable(value = "id") int id,
                                      Model model) {
        courseService.deleteUserCourse(id, user.getId());
        return "redirect:/account";
    }

    @GetMapping("/account/test")
    public String accountTest(@AuthenticationPrincipal User user, Model model) {
        User userDB = userService.findUserById(user.getId());
        model.addAttribute("user", userDB);
        return "test";
    }
}
