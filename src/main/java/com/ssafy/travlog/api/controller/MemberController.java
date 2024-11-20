package com.ssafy.travlog.api.controller;

import com.ssafy.travlog.api.dto.MemberInfoResponse;
import com.ssafy.travlog.api.dto.PublicMemberInfo;
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
        PublicMemberInfo publicMemberInfo = memberService.getMemberByPublicId(publicId);
        if (publicMemberInfo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new MemberInfoResponse(publicMemberInfo));
    }

    @GetMapping("/me")
    public ResponseEntity<MemberInfoResponse> getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String publicId = authentication.getName();
        PublicMemberInfo publicMemberInfo = memberService.getMemberByPublicId(publicId);
        if (publicMemberInfo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new MemberInfoResponse(publicMemberInfo));
    }
}
