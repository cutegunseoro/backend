package com.ssafy.travlog.api.controller;

import com.ssafy.travlog.api.dto.video.VideoMetaInsertRequest;
import com.ssafy.travlog.api.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) throws IOException {
        String videoUrl = videoService.saveVideo(file);
        return ResponseEntity.ok(videoUrl);
    }

    @PostMapping
    public ResponseEntity<String> uploadVideoMetadata(@RequestBody VideoMetaInsertRequest videoMetaInsertRequest) {
        int result = videoService.uploadVideoMetadata(videoMetaInsertRequest);
        if (result == 0) {
            return ResponseEntity.badRequest().body("Failed to upload video metadata");
        }
        return ResponseEntity.ok("Video metadata uploaded successfully");
    }
}
