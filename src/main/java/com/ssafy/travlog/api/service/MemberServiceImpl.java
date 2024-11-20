package com.ssafy.travlog.api.service;

import com.ssafy.travlog.api.dto.LoginRequest;
import com.ssafy.travlog.api.dto.PublicMemberInfo;
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
    public PublicMemberInfo getMemberByMemberId(long memberId) {
        MemberModel memberModel = memberMapper.selectMemberByMemberId(memberId);
        return new PublicMemberInfo(memberModel.getPublic_id(), memberModel.getDisplay_name(), memberModel.getBio());
    }

    @Override
    public PublicMemberInfo getMemberByLoginId(String loginId) {
        MemberModel memberModel = memberMapper.selectMemberByLoginId(loginId);
        return new PublicMemberInfo(memberModel.getPublic_id(), memberModel.getDisplay_name(), memberModel.getBio());
    }

    @Override
    public PublicMemberInfo getMemberByPublicId(String publicId) {
        MemberModel memberModel = memberMapper.selectMemberByPublicId(publicId);
        return new PublicMemberInfo(memberModel.getPublic_id(), memberModel.getDisplay_name(), memberModel.getBio());
    }

    @Override
    public PublicMemberInfo login(LoginRequest loginRequest) {
        MemberModel memberModel = memberMapper.selectMemberByLoginId(loginRequest.getLoginId());
        if (passwordEncoder.matches(loginRequest.getPassword(), memberModel.getHashed_password())) {
            return new PublicMemberInfo(memberModel.getPublic_id(), memberModel.getDisplay_name(), memberModel.getBio());
        }
        return null;
    }

    @Override
    public int signup(SignupRequest signupRequest) {
        MemberModel memberModel = new MemberModel();
        memberModel.setLogin_id(signupRequest.getLoginId());
        memberModel.setHashed_password(passwordEncoder.encode(signupRequest.getPassword()));
        memberModel.setPublic_id(signupRequest.getPublicId());
        return memberMapper.insertMember(memberModel);
    }
}
