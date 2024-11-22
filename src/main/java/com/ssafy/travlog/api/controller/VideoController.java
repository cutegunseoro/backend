package com.ssafy.travlog.api.controller;

import com.ssafy.travlog.api.dto.video.VideoFileUploadResponse;
import com.ssafy.travlog.api.dto.video.VideoMetadataUploadRequest;
import com.ssafy.travlog.api.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<VideoFileUploadResponse> uploadVideoFile(
            Authentication authentication,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        VideoFileUploadResponse res = videoService.uploadVideoFile(authentication, file);
        return ResponseEntity.ok(res);
    }

    @PutMapping
    public ResponseEntity<String> uploadVideoMetadata(
            Authentication authentication,
            @RequestBody VideoMetadataUploadRequest videoMetadataUploadRequest
    ) {
        int result = videoService.uploadVideoMetadata(authentication, videoMetadataUploadRequest);
        if (result == 0) {
            return ResponseEntity.badRequest().body("Failed to upload video metadata");
        }
        return ResponseEntity.ok("Video metadata uploaded successfully");
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<String> getVideoMetadata(
            @PathVariable Long videoId
    ) {
        return ResponseEntity.ok(videoService.getVideoMetadata(videoId).toString());
    }
}
