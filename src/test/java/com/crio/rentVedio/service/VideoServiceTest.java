package com.crio.rentVedio.service;

import com.crio.rentVedio.model.Video;
import com.crio.rentVedio.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class VideoServiceTest {
    @Mock
    private VideoRepository videoRepository;
    @InjectMocks
    private VideoService videoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllVideos() {
        Video v1 = new Video();
        v1.setTitle("A");
        Video v2 = new Video();
        v2.setTitle("B");
        when(videoRepository.findAll()).thenReturn(Arrays.asList(v1, v2));
        List<Video> videos = videoService.getAllVideos();
        assertEquals(2, videos.size());
    }

    @Test
    void testGetVideoById() {
        Video v = new Video();
        v.setId(1L);
        when(videoRepository.findById(1L)).thenReturn(Optional.of(v));
        Optional<Video> found = videoService.getVideoById(1L);
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
    }

    @Test
    void testCreateVideo() {
        Video v = new Video();
        v.setTitle("Test");
        when(videoRepository.save(any(Video.class))).thenReturn(v);
        Video created = videoService.createVideo(v);
        assertEquals("Test", created.getTitle());
    }

    @Test
    void testUpdateVideo() {
        Video v = new Video();
        v.setId(1L);
        v.setTitle("Old");
        Video updated = new Video();
        updated.setTitle("New");
        updated.setDirector("Dir");
        updated.setGenre("Genre");
        updated.setAvailabilityStatus(true);
        when(videoRepository.findById(1L)).thenReturn(Optional.of(v));
        when(videoRepository.save(any(Video.class))).thenReturn(updated);
        Video result = videoService.updateVideo(1L, updated);
        assertEquals("New", result.getTitle());
    }

    @Test
    void testDeleteVideo() {
        // No exception means pass
        videoService.deleteVideo(1L);
    }
}
