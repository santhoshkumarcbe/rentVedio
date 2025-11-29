package com.crio.rentVedio.controller;

import com.crio.rentVedio.model.User;
import com.crio.rentVedio.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private AuthController authController;

    public AuthControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userService.registerUser(any(User.class))).thenReturn(user);
        ResponseEntity<User> response = authController.registerUser(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("test@example.com", response.getBody().getEmail());
    }
}
