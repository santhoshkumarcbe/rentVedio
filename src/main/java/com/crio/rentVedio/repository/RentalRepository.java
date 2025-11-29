package com.crio.rentVedio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crio.rentVedio.model.Rental;
import com.crio.rentVedio.model.RentalStatus;
import com.crio.rentVedio.model.User;
import com.crio.rentVedio.model.Video;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    long countByUserAndStatus(User user, RentalStatus status);
    Optional<Rental> findByUserAndVideoAndStatus(User user, Video video, RentalStatus status);
    List<Rental> findByUserAndStatus(User user, RentalStatus status);
}
