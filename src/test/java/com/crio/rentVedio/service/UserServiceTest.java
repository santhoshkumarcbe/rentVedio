package com.crio.rentVedio.service;

import com.crio.rentVedio.model.Role;
import com.crio.rentVedio.model.User;
import com.crio.rentVedio.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_DefaultRoleAndPasswordEncoding() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("plain");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setRole(null);

        when(passwordEncoder.encode("plain")).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User saved = userService.registerUser(user);
        assertEquals(Role.CUSTOMER, saved.getRole());
        assertEquals("hashed", saved.getPassword());
    }

    @Test
    void testFindByEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        Optional<User> found = userService.findByEmail("test@example.com");
        assertTrue(found.isPresent());
        assertEquals("test@example.com", found.get().getEmail());
    }
}
