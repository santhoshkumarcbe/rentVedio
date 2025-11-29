package com.crio.rentVedio.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crio.rentVedio.model.Rental;
import com.crio.rentVedio.model.RentalStatus;
import com.crio.rentVedio.model.User;
import com.crio.rentVedio.model.Video;
import com.crio.rentVedio.repository.RentalRepository;
import com.crio.rentVedio.repository.UserRepository;
import com.crio.rentVedio.repository.VideoRepository;

import jakarta.transaction.Transactional;

@Service
public class RentalService {

    @Autowired private RentalRepository rentalRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private VideoRepository videoRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Transactional
    public void rentVideo(String userEmail, Long videoId) {
        User user = userRepo.findByEmail(userEmail).orElseThrow(null);
        Video video = videoRepo.findById(videoId).orElseThrow(null);

        if (!video.isAvailabilityStatus()) {
            throw new IllegalStateException("Video not available");
        }

        long activeCount = rentalRepo.countByUserAndStatus(user, RentalStatus.ACTIVE);
        if (activeCount >= 2) {
            throw new IllegalStateException("User already has two active rentals");
        }

        // create rental
        Rental rental = new Rental();
        rental.setUser(user);
        rental.setVideo(video);
        rental.setStatus(RentalStatus.ACTIVE);
        rental.setRentedAt(Instant.now());
        rentalRepo.save(rental);

        // mark video unavailable
        video.setAvailabilityStatus(false);
        videoRepo.save(video);
    }

    @Transactional
    public void returnVideo(String userEmail, Long videoId) {
        User user = userRepo.findByEmail(userEmail).orElseThrow(null);
        Video video = videoRepo.findById(videoId).orElseThrow(null);

        var rentOpt = rentalRepo.findByUserAndVideoAndStatus(user, video, RentalStatus.ACTIVE);
        if (rentOpt.isEmpty()) throw new IllegalStateException("No active rental found for this user and video");

        var rental = rentOpt.get();
        rental.setStatus(RentalStatus.RETURNED);
        rental.setReturnedAt(Instant.now());
        rentalRepo.save(rental);

        video.setAvailabilityStatus(true);
        videoRepo.save(video);
    }
}
