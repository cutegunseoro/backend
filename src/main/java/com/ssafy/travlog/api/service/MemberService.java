package com.ssafy.travlog.api.service;

import com.ssafy.travlog.api.dto.LoginRequest;
import com.ssafy.travlog.api.dto.PublicMemberInfo;
import com.ssafy.travlog.api.dto.SignupRequest;

public interface MemberService {

    PublicMemberInfo getMemberByMemberId(long memberId);

    PublicMemberInfo getMemberByLoginId(String loginId);

    PublicMemberInfo getMemberByPublicId(String publicId);

    PublicMemberInfo login(LoginRequest loginRequest);

    int signup(SignupRequest signupRequest);
}
