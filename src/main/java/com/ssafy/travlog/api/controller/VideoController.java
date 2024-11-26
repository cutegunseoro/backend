package com.ssafy.travlog.api.controller;

import com.ssafy.travlog.api.dto.video.*;
import com.ssafy.travlog.api.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @GetMapping("/upload-url")
    public ResponseEntity<VideoFileUploadUrlResponse> getVideoFileUploadUrl(
            Authentication authentication,
            @RequestParam("contentType") String contentType
    ) {
        var res = videoService.generateVideoFileUploadUrl(authentication, contentType);
        return ResponseEntity.ok().body(res);
    }

    @PutMapping
    public ResponseEntity<Void> uploadVideoMetadata(
            Authentication authentication,
            @RequestBody VideoMetadataUploadRequest videoMetadataUploadRequest
    ) {
        videoService.uploadVideoMetadata(authentication, videoMetadataUploadRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoMetadataResponse> getVideoMetadata(
            @PathVariable Long videoId
    ) {
        var res = videoService.getVideoMetadata(videoId);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{videoId}/stream-url")
    public ResponseEntity<VideoFileStreamUrlResponse> getVideoFileStreamUrl(
            Authentication authentication,
            @PathVariable Long videoId
    ) {
        var res = videoService.generateVideoFileStreamUrl(authentication, videoId);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/search")
    public ResponseEntity<VideoMetadataListResponse> searchVideoMetadata(
            @RequestParam String coordinates,
            @RequestParam(required = false) Integer distance
    ) {
        if (coordinates != null && !coordinates.isEmpty()) {
            var res = videoService.getVideoMetadataListByLocationAndDistance(coordinates, distance);
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.badRequest().build();
    }
}
