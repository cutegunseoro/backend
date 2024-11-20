package com.ssafy.travlog.api.controller;

import com.ssafy.travlog.api.dto.*;
import com.ssafy.travlog.api.service.MemberService;
import com.ssafy.travlog.api.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    public final JwtUtil jwtUtil;
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
            String accessToken = jwtUtil.createAccessToken(member.getPublicId());
            return ResponseEntity.ok(new LoginResponse(accessToken, member));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
