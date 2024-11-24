package com.ssafy.travlog.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.travlog.api.dto.member.MemberInfoResponse;
import com.ssafy.travlog.api.dto.travel.TravelInfoListResponse;
import com.ssafy.travlog.api.dto.video.VideoMetadataListResponse;
import com.ssafy.travlog.api.service.MemberService;
import com.ssafy.travlog.api.service.TravelService;
import com.ssafy.travlog.api.service.VideoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final TravelService travelService;
	private final VideoService videoService;

	@GetMapping("/{publicId}")
	public ResponseEntity<MemberInfoResponse> getMemberInfo(@PathVariable String publicId) {
		var res = memberService.getMemberByPublicId(publicId);
		return ResponseEntity.ok(res);
	}

	@GetMapping("/{publicId}/travels")
	public ResponseEntity<TravelInfoListResponse> getTravels(@PathVariable String publicId) {
		var res = travelService.getTravelInfoListOfMember(publicId);
		return ResponseEntity.ok(res);
	}

	@GetMapping("/{publicId}/videos")
	public ResponseEntity<VideoMetadataListResponse> getVideosOfMember(@PathVariable String publicId) {
		var res = videoService.getVideoMetadataListOfMember(publicId);
		if (res == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(res);
	}
}
