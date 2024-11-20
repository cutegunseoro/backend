package com.ssafy.travlog.api.controller;

import com.ssafy.travlog.api.dto.Member;
import com.ssafy.travlog.api.dto.MemberInfoResponse;
import com.ssafy.travlog.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<MemberInfoResponse> getUserInfo(@PathVariable String publicId) {
        Member member = memberService.getMemberByPublicId(publicId);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new MemberInfoResponse(member));
    }

    @GetMapping("/me")
    public ResponseEntity<MemberInfoResponse> getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String publicId = authentication.getName();
        Member member = memberService.getMemberByPublicId(publicId);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new MemberInfoResponse(member));
    }
}
