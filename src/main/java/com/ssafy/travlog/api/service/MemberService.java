package com.ssafy.travlog.api.service;

import com.ssafy.travlog.api.dto.LoginRequest;
import com.ssafy.travlog.api.dto.MemberDto;
import com.ssafy.travlog.api.dto.SignupDto;

public interface MemberService {

    MemberDto getMemberByMemberId(long memberId);

    MemberDto getMemberByLoginId(String loginId);

    MemberDto getMemberByPublicId(String publicId);

    MemberDto login(LoginRequest loginRequest);

    int signup(SignupDto signupDto);
}
