package com.ssafy.travlog.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.travlog.api.dto.me.MeInfoResponse;
import com.ssafy.travlog.api.service.MeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class MeController {
	private final MeService meService;

	@GetMapping
	public ResponseEntity<MeInfoResponse> me(Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(401).build();
		}
		var res = meService.getMeInfo(authentication);
		return ResponseEntity.ok(res);
	}
}
