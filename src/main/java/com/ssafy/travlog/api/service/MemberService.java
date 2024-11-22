package com.ssafy.travlog.api.service;

import com.ssafy.travlog.api.dto.auth.LoginRequest;
import com.ssafy.travlog.api.dto.auth.LoginResponse;
import com.ssafy.travlog.api.dto.auth.SignupRequest;
import com.ssafy.travlog.api.dto.member.MemberInfo;
import com.ssafy.travlog.api.dto.member.MemberInfoResponse;
import com.ssafy.travlog.api.mapper.MemberMapper;
import com.ssafy.travlog.api.model.MemberInsertModel;
import com.ssafy.travlog.api.model.MemberModel;
import com.ssafy.travlog.api.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    public final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;

    public MemberInfoResponse getMemberByMemberId(long memberId) {
        MemberModel memberModel = memberMapper.selectMemberByMemberId(memberId);
        MemberInfo memberInfo = new MemberInfo(memberModel.getPublicId(), memberModel.getDisplayName(), memberModel.getBio());
        return new MemberInfoResponse(memberInfo);
    }

    public MemberInfoResponse getMemberByLoginId(String loginId) {
        MemberModel memberModel = memberMapper.selectMemberByLoginId(loginId);
        MemberInfo memberInfo = new MemberInfo(memberModel.getPublicId(), memberModel.getDisplayName(), memberModel.getBio());
        return new MemberInfoResponse(memberInfo);
    }

    public MemberInfoResponse getMemberByPublicId(String publicId) {
        MemberModel memberModel = memberMapper.selectMemberByPublicId(publicId);
        MemberInfo memberInfo = new MemberInfo(memberModel.getPublicId(), memberModel.getDisplayName(), memberModel.getBio());
        return new MemberInfoResponse(memberInfo);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        MemberModel memberModel = memberMapper.selectMemberByLoginId(loginRequest.getLoginId());
        if (!passwordEncoder.matches(loginRequest.getPassword(), memberModel.getHashedPassword())) {
            throw new IllegalArgumentException("Password is incorrect");
        }
        MemberInfo memberInfo = new MemberInfo(memberModel.getPublicId(), memberModel.getDisplayName(), memberModel.getBio());
        String accessToken = jwtUtil.createAccessToken(memberInfo.getPublicId());
        return new LoginResponse(accessToken, memberInfo);
    }

    public void signup(SignupRequest signupRequest) {
        MemberInsertModel memberInsertModel = MemberInsertModel.builder()
                .loginId(signupRequest.getLoginId())
                .hashedPassword(passwordEncoder.encode(signupRequest.getPassword()))
                .publicId(signupRequest.getPublicId())
                .build();
        int result = memberMapper.insertMember(memberInsertModel);
        if (result != 1) {
            throw new IllegalArgumentException("Failed to insert member");
        }
    }
}
