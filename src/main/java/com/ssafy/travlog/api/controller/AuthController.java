package com.ssafy.travlog.api.controller;

import com.ssafy.travlog.api.dto.auth.LoginRequest;
import com.ssafy.travlog.api.dto.auth.LoginResponse;
import com.ssafy.travlog.api.dto.auth.SignupRequest;
import com.ssafy.travlog.api.dto.auth.SignupResponse;
import com.ssafy.travlog.api.dto.member.MemberInfo;
import com.ssafy.travlog.api.service.MemberService;
import com.ssafy.travlog.api.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    public final JwtUtil jwtUtil;
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest) {
        int result = memberService.signup(signupRequest);

        if (result == 1) {
            return ResponseEntity.ok(new SignupResponse("success"));
        } else {
            return ResponseEntity.badRequest().body(new SignupResponse("fail"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        MemberInfo memberInfo = memberService.login(loginRequest);
        if (memberInfo != null) {
            String accessToken = jwtUtil.createAccessToken(memberInfo.getPublicId());
            return ResponseEntity.ok(new LoginResponse(accessToken, memberInfo));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
