package com.ssafy.travlog.api.service;

import com.ssafy.travlog.api.dto.auth.LoginRequest;
import com.ssafy.travlog.api.dto.auth.SignupRequest;
import com.ssafy.travlog.api.dto.member.MemberInfo;

public interface MemberService {

    MemberInfo getMemberByMemberId(long memberId);

    MemberInfo getMemberByLoginId(String loginId);

    MemberInfo getMemberByPublicId(String publicId);

    MemberInfo login(LoginRequest loginRequest);

    int signup(SignupRequest signupRequest);
}
