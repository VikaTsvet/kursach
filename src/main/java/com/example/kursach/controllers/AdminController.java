package com.example.kursach.controllers;

import com.example.kursach.models.Role;
import com.example.kursach.models.User;
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

import java.io.IOException;
import java.util.Collections;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @GetMapping("/admin/add")
    public String addUser(Model model) {
        return "user-add";
    }

    @PostMapping("/admin/add")
    public String addUserPost(@RequestParam String name, @RequestParam String surname, @RequestParam int age,
                              @RequestParam String login, @RequestParam String password, Model model) {
        User user = new User(name, surname, age, login, password);
        user.setRoles(Collections.singleton(new Role(3, "TEACHER")));
        if (!userService.saveUser(user)) {
            model.addAttribute("usernameError", true);
            return "user-add";
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin")
    public String deleteUser(@RequestParam int userId, Model model) {
        if (!userService.deleteUser(userId)) {
            model.addAttribute("usernameError", true);
            model.addAttribute("allUsers", userService.allUsers());
            return "admin";
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String editUser(@PathVariable(value = "id") int id, Model model) {
        if (!userService.existsById(id)) {
            return "redirect:/admin";
        }
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping("/admin/{id}/edit")
    public String editUserPost(@PathVariable(value = "id") int id, @RequestParam String name, @RequestParam String surname,
                               @RequestParam int age, @RequestParam String login, Model model) throws IOException {
        userService.editUser(id, name, surname, age, login, null);
        return "redirect:/admin";
    }

    public void setUserService(UserService userService) {
    }
}
