package com.ssafy.travlog.api.controller;

import com.ssafy.travlog.api.dto.*;
import com.ssafy.travlog.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@ModelAttribute SignupRequest signupRequest) {
        int result = memberService.signup(signupRequest);

        if (result == 1) {
            return ResponseEntity.ok(new SignupResponse("success"));
        } else {
            return ResponseEntity.badRequest().body(new SignupResponse("fail"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@ModelAttribute LoginRequest loginRequest) {
        Member member = memberService.login(loginRequest);
        if (member != null) {
            return ResponseEntity.ok(new LoginResponse("accessToken", member));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}