package com.ssafy.travlog.api.controller;

import com.ssafy.travlog.api.dto.member.MemberInfoResponse;
import com.ssafy.travlog.api.dto.video.VideoMetadataListResponse;
import com.ssafy.travlog.api.service.MemberService;
import com.ssafy.travlog.api.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final VideoService videoService;

    @GetMapping("/{publicId}")
    public ResponseEntity<MemberInfoResponse> getMemberInfo(@PathVariable String publicId, Authentication authentication) {
        String tokenPublicId = authentication.getName();
        var res = memberService.getMemberByPublicId(publicId);
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
