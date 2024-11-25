package com.ssafy.travlog.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.travlog.api.dto.video.VideoFileUploadUrlResponse;
import com.ssafy.travlog.api.dto.video.VideoMetadataListResponse;
import com.ssafy.travlog.api.dto.video.VideoMetadataResponse;
import com.ssafy.travlog.api.dto.video.VideoMetadataUploadRequest;
import com.ssafy.travlog.api.service.VideoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
