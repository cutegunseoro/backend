package com.ssafy.travlog.api.controller;

import com.ssafy.travlog.api.dto.MemberDto;
import com.ssafy.travlog.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public MemberDto getMember(@PathVariable long memberId) {
        return memberService.getMember(memberId);
    }
}
