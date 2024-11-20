package com.ssafy.travlog.api.service;

import com.ssafy.travlog.api.dto.LoginRequest;
import com.ssafy.travlog.api.dto.Member;
import com.ssafy.travlog.api.dto.SignupDto;

public interface MemberService {

    Member getMemberByMemberId(long memberId);

    Member getMemberByLoginId(String loginId);

    Member getMemberByPublicId(String publicId);

    Member login(LoginRequest loginRequest);

    int signup(SignupDto signupDto);
}
