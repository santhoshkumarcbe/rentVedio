package com.crio.rentVedio.repository;

import com.crio.rentVedio.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
