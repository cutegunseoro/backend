package com.ssafy.travlog.api.controller;

import com.ssafy.travlog.api.dto.LoginRequest;
import com.ssafy.travlog.api.dto.MemberDto;
import com.ssafy.travlog.api.dto.SignupDto;
import com.ssafy.travlog.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public MemberDto login(@ModelAttribute
                           LoginRequest loginRequest) {
        return memberService.login(loginRequest);
    }

    @PostMapping("/signup")
    public int signup(@ModelAttribute SignupDto signupDto) {
        return memberService.signup(signupDto);
    }
}
