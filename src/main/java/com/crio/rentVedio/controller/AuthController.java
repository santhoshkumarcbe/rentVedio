package com.crio.rentVedio.controller;

import com.crio.rentVedio.model.User;
import com.crio.rentVedio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
