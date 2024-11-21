package com.ssafy.travlog.api.controller;

import com.ssafy.travlog.api.dto.member.MemberInfo;
import com.ssafy.travlog.api.dto.member.MemberInfoResponse;
import com.ssafy.travlog.api.service.MemberService;
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

    @GetMapping("/{publicId}")
    public ResponseEntity<MemberInfoResponse> getMemberInfo(@PathVariable String publicId, Authentication authentication) {
        String tokenPublicId = authentication.getName();
        MemberInfo memberInfo = memberService.getMemberByPublicId(publicId);
        if (memberInfo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new MemberInfoResponse(memberInfo));
    }

    @GetMapping("/{publicId}/videos")
    public ResponseEntity<MemberInfoResponse> getMemberVideos(@PathVariable String publicId) {
//        MemberInfo memberInfo = memberService.getMemberByPublicId(publicId);
//        if (memberInfo == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(new MemberInfoResponse(memberInfo));
        return null;
    }
}
