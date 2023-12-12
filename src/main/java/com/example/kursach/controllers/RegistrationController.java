package com.example.kursach.controllers;

import com.example.kursach.models.Role;
import com.example.kursach.models.User;
import com.example.kursach.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String name, @RequestParam String surname, @RequestParam int age,
                          @RequestParam String login, @RequestParam String password, Model model) {
        User user = new User(name, surname, age, login, password);
        user.setRoles(Collections.singleton(new Role(1, "USER")));
        if (!userService.saveUser(user)){
            model.addAttribute("usernameError", true);
            return "/registration";
        }
        return "redirect:/";
    }
}
