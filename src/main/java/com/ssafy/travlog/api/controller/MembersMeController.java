package com.ssafy.travlog.api.controller;

import com.ssafy.travlog.api.dto.MemberInfo;
import com.ssafy.travlog.api.dto.MemberInfoResponse;
import com.ssafy.travlog.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members/me")
@RequiredArgsConstructor
public class MembersMeController {
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberInfoResponse> getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String publicId = authentication.getName();
        MemberInfo memberInfo = memberService.getMemberByPublicId(publicId);
        if (memberInfo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new MemberInfoResponse(memberInfo));
    }
}
