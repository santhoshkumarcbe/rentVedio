package com.crio.rentVedio.controller;

import com.crio.rentVedio.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping("/videos/{videoId}/rent")
    public ResponseEntity<?> rentVideo(@AuthenticationPrincipal UserDetails userDetails,
                                       @PathVariable Long videoId) {
        rentalService.rentVideo(userDetails.getUsername(), videoId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/videos/{videoId}/return")
    public ResponseEntity<?> returnVideo(@AuthenticationPrincipal UserDetails userDetails,
                                         @PathVariable Long videoId) {
        rentalService.returnVideo(userDetails.getUsername(), videoId);
        return ResponseEntity.ok().build();
    }
}
