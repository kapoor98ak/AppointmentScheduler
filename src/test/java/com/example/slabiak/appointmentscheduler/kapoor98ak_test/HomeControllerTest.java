package com.example.slabiak.appointmentscheduler.kapoor98ak_test;

import com.example.slabiak.appointmentscheduler.controller.HomeController;
import com.example.slabiak.appointmentscheduler.entity.user.User;
import com.example.slabiak.appointmentscheduler.security.CustomUserDetails;
import com.example.slabiak.appointmentscheduler.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class HomeControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private CustomUserDetails customUserDetails;

    @InjectMocks
    private HomeController homeController;

    @Test
    void showHome() {
        // Arrange
        when(customUserDetails.getId()).thenReturn(1);
        when(userService.getUserById(1)).thenReturn(Mockito.mock(User.class));

        // Act
        String viewName = homeController.showHome(model, customUserDetails);

        // Assert
        assertEquals("home", viewName);
        Mockito.verify(model).addAttribute("user", Mockito.mock(User.class));
    }

    @Test
    void loginUserNotNullRedirect() {
        // Arrange
        when(customUserDetails != null).thenReturn(true);

        // Act
        String viewName = homeController.login(model, customUserDetails);

        // Assert
        assertEquals("redirect:/", viewName);
    }

    @Test
    void loginUserNullReturnLoginView() {
        // Arrange
        when(customUserDetails != null).thenReturn(false);

        // Act
        String viewName = homeController.login(model, customUserDetails);

        // Assert
        assertEquals("users/login", viewName);
    }

    @Test
    void showAccessDeniedPage() {
        // Act
        String viewName = homeController.showAccessDeniedPage();

        // Assert
        assertEquals("access-denied", viewName);
    }
}

