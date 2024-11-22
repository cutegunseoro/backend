package com.ssafy.travlog.api.service;

import com.ssafy.travlog.api.dto.auth.LoginRequest;
import com.ssafy.travlog.api.dto.auth.SignupRequest;
import com.ssafy.travlog.api.dto.member.MemberInfo;
import com.ssafy.travlog.api.mapper.MemberMapper;
import com.ssafy.travlog.api.model.MemberInsertModel;
import com.ssafy.travlog.api.model.MemberModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;

    public MemberInfo getMemberByMemberId(long memberId) {
        MemberModel memberModel = memberMapper.selectMemberByMemberId(memberId);
        return new MemberInfo(memberModel.getPublicId(), memberModel.getDisplayName(), memberModel.getBio());
    }

    public MemberInfo getMemberByLoginId(String loginId) {
        MemberModel memberModel = memberMapper.selectMemberByLoginId(loginId);
        return new MemberInfo(memberModel.getPublicId(), memberModel.getDisplayName(), memberModel.getBio());
    }

    public MemberInfo getMemberByPublicId(String publicId) {
        MemberModel memberModel = memberMapper.selectMemberByPublicId(publicId);
        return new MemberInfo(memberModel.getPublicId(), memberModel.getDisplayName(), memberModel.getBio());
    }

    public MemberInfo login(LoginRequest loginRequest) {
        MemberModel memberModel = memberMapper.selectMemberByLoginId(loginRequest.getLoginId());
        if (passwordEncoder.matches(loginRequest.getPassword(), memberModel.getHashedPassword())) {
            return new MemberInfo(memberModel.getPublicId(), memberModel.getDisplayName(), memberModel.getBio());
        }
        return null;
    }

    public int signup(SignupRequest signupRequest) {
        MemberInsertModel memberInsertModel = MemberInsertModel.builder()
                .loginId(signupRequest.getLoginId())
                .hashedPassword(passwordEncoder.encode(signupRequest.getPassword()))
                .publicId(signupRequest.getPublicId())
                .build();
        return memberMapper.insertMember(memberInsertModel);
    }
}
