package com.ssafy.travlog.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.travlog.api.dto.travel.TravelAddRequest;
import com.ssafy.travlog.api.service.TravelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/travels")
@RequiredArgsConstructor
public class TravelController {
	private final TravelService travelService;

	@PostMapping
	public ResponseEntity<Void> addTravel(Authentication authentication, @RequestBody TravelAddRequest travelAddRequest) {
		String publicId = authentication.getName();
		travelAddRequest.setPublicId(publicId);
		travelService.addTravel(travelAddRequest);
		return ResponseEntity.ok().build();
	}
}
