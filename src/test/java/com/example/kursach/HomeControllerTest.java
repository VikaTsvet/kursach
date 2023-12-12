package com.example.kursach;

import com.example.kursach.controllers.HomeController;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class HomeControllerTest {

    private HomeController homeController;
    @Mock
    private Model model;
    @Test
    public void testHome() {
        // Arrange
        MockitoAnnotations.initMocks(this);
        homeController = new HomeController();
        String viewName = homeController.home(model);
        assertEquals("home", viewName);
        verify(model, times(1)).addAttribute("title", "Главная страница");
    }
}

