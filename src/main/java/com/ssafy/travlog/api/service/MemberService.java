package com.ssafy.travlog.api.service;

import com.ssafy.travlog.api.dto.LoginRequest;
import com.ssafy.travlog.api.dto.MemberInfo;
import com.ssafy.travlog.api.dto.SignupRequest;

public interface MemberService {

    MemberInfo getMemberByMemberId(long memberId);

    MemberInfo getMemberByLoginId(String loginId);

    MemberInfo getMemberByPublicId(String publicId);

    MemberInfo login(LoginRequest loginRequest);

    int signup(SignupRequest signupRequest);
}
