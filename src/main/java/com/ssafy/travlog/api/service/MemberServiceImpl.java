package com.ssafy.travlog.api.service;

import com.ssafy.travlog.api.dto.LoginRequest;
import com.ssafy.travlog.api.dto.MemberInfo;
import com.ssafy.travlog.api.dto.SignupRequest;
import com.ssafy.travlog.api.mapper.MemberMapper;
import com.ssafy.travlog.api.model.MemberModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;

    @Override
    public MemberInfo getMemberByMemberId(long memberId) {
        MemberModel memberModel = memberMapper.selectMemberByMemberId(memberId);
        return new MemberInfo(memberModel.getPublicId(), memberModel.getDisplayName(), memberModel.getBio());
    }

    @Override
    public MemberInfo getMemberByLoginId(String loginId) {
        MemberModel memberModel = memberMapper.selectMemberByLoginId(loginId);
        return new MemberInfo(memberModel.getPublicId(), memberModel.getDisplayName(), memberModel.getBio());
    }

    @Override
    public MemberInfo getMemberByPublicId(String publicId) {
        MemberModel memberModel = memberMapper.selectMemberByPublicId(publicId);
        return new MemberInfo(memberModel.getPublicId(), memberModel.getDisplayName(), memberModel.getBio());
    }

    @Override
    public MemberInfo login(LoginRequest loginRequest) {
        MemberModel memberModel = memberMapper.selectMemberByLoginId(loginRequest.getLoginId());
        if (passwordEncoder.matches(loginRequest.getPassword(), memberModel.getHashedPassword())) {
            return new MemberInfo(memberModel.getPublicId(), memberModel.getDisplayName(), memberModel.getBio());
        }
        return null;
    }

    @Override
    public int signup(SignupRequest signupRequest) {
        MemberModel memberModel = new MemberModel();
        memberModel.setLoginId(signupRequest.getLoginId());
        memberModel.setHashedPassword(passwordEncoder.encode(signupRequest.getPassword()));
        memberModel.setPublicId(signupRequest.getPublicId());
        return memberMapper.insertMember(memberModel);
    }
}
