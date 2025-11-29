package com.crio.rentVedio.service;

import com.crio.rentVedio.model.Video;
import com.crio.rentVedio.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    public Optional<Video> getVideoById(Long id) {
        return videoRepository.findById(id);
    }

    public Video createVideo(Video video) {
        return videoRepository.save(video);
    }

    public Video updateVideo(Long id, Video videoDetails) {
        Video video = videoRepository.findById(id).orElseThrow();
        video.setTitle(videoDetails.getTitle());
        video.setDirector(videoDetails.getDirector());
        video.setGenre(videoDetails.getGenre());
        video.setAvailabilityStatus(videoDetails.isAvailabilityStatus());
        return videoRepository.save(video);
    }

    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }
}
