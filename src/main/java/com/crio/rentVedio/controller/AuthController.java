package com.crio.rentVedio.controller;

import com.crio.rentVedio.model.User;
import com.crio.rentVedio.service.UserService;
import com.crio.rentVedio.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User saved = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        User user = userService.authenticate(req.email(), req.password());
        List<String> roles = List.of(user.getRole().name());
        String token = jwtUtil.generateToken(user.getEmail(), roles);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    public static record LoginRequest(String email, String password) {
    }

    public static record LoginResponse(String token) {
    }
}
