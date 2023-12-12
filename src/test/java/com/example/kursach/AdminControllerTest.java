package com.example.kursach;

import com.example.kursach.controllers.AdminController;
import com.example.kursach.models.Role;
import com.example.kursach.models.User;
import com.example.kursach.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AdminControllerTest {

    private AdminController adminController;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        adminController = new AdminController();
        adminController.setUserService(userService);
    }

    @Test
    public void testAddUser() {
        // Act
        String viewName = adminController.addUser(model);

        // Assert
        assertEquals("user-add", viewName);
    }

    @Test
    public void testAddUserPost_Success() {
        // Arrange
        String name = "John";
        String surname = "Doe";
        int age = 25;
        String login = "johndoe";
        String password = "password";
        User user = new User(name, surname, age, login, password);
        user.setRoles(Collections.singleton(new Role(3, "TEACHER")));

        when(userService.saveUser(user)).thenReturn(true);

        // Act
        String viewName = adminController.addUserPost(name, surname, age, login, password, model);

        // Assert
        assertEquals("redirect:/admin", viewName);
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    public void testAddUserPost_Failure() {
        // Arrange
        String name = "John";
        String surname = "Doe";
        int age = 25;
        String login = "johndoe";
        String password = "password";
        User user = new User(name, surname, age, login, password);
        user.setRoles(Collections.singleton(new Role(3, "TEACHER")));

        when(userService.saveUser(user)).thenReturn(false);

        // Act
        String viewName = adminController.addUserPost(name, surname, age, login, password, model);

        // Assert
        assertEquals("user-add", viewName);
        verify(model, times(1)).addAttribute("usernameError", true);
        verify(userService, times(1)).saveUser(user);
    }

    // Add tests for other methods in AdminController

}