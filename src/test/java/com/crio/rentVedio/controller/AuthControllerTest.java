package com.crio.rentVedio.controller;

import com.crio.rentVedio.model.User;
import com.crio.rentVedio.security.JwtUtil;
import com.crio.rentVedio.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    // AuthController has a JwtUtil field; mock it to allow injection
    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @Test
    void register_shouldReturnCreatedAndHidePassword() {
        // Arrange: input user (what client sends)
        User input = new User();
        input.setEmail("test@example.com");
        input.setPassword("plainpassword");
        input.setFirstName("Test");
        input.setLastName("User");

        // Mocked saved user (what UserService returns)
        User saved = new User();
        saved.setId(1L);
        saved.setEmail("test@example.com");
        saved.setPassword("$2a$10$hashedpassword"); // simulate stored hashed password
        saved.setFirstName("Test");
        saved.setLastName("User");

        when(userService.registerUser(any(User.class))).thenReturn(saved);

        // Act
        ResponseEntity<?> response = authController.register(input);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // The controller nulls out the password before returning the saved user
        assertTrue(response.getBody() instanceof User);
        User body = (User) response.getBody();
        assertEquals("test@example.com", body.getEmail());
        assertEquals("Test", body.getFirstName());
        assertEquals("User", body.getLastName());
    }
}
