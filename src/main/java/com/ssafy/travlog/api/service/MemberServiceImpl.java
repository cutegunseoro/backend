package com.ssafy.travlog.api.service;

import com.ssafy.travlog.api.dto.LoginRequest;
import com.ssafy.travlog.api.dto.Member;
import com.ssafy.travlog.api.dto.SignupDto;
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
    public Member getMemberByMemberId(long memberId) {
        MemberModel memberModel = memberMapper.selectMemberByMemberId(memberId);
        return new Member(memberModel.getPublic_id(), memberModel.getDisplay_name(), memberModel.getBio());
    }

    @Override
    public Member getMemberByLoginId(String loginId) {
        MemberModel memberModel = memberMapper.selectMemberByLoginId(loginId);
        return new Member(memberModel.getPublic_id(), memberModel.getDisplay_name(), memberModel.getBio());
    }

    @Override
    public Member getMemberByPublicId(String publicId) {
        MemberModel memberModel = memberMapper.selectMemberByPublicId(publicId);
        return new Member(memberModel.getPublic_id(), memberModel.getDisplay_name(), memberModel.getBio());
    }

    @Override
    public Member login(LoginRequest loginRequest) {
        MemberModel memberModel = memberMapper.selectMemberByLoginId(loginRequest.getLoginId());
        if (passwordEncoder.matches(loginRequest.getPassword(), memberModel.getHashed_password())) {
            return new Member(memberModel.getPublic_id(), memberModel.getDisplay_name(), memberModel.getBio());
        }
        return null;
    }

    @Override
    public int signup(SignupDto signupDto) {
        MemberModel memberModel = new MemberModel();
        memberModel.setLogin_id(signupDto.getLoginId());
        memberModel.setHashed_password(passwordEncoder.encode(signupDto.getPassword()));
        memberModel.setPublic_id(signupDto.getPublicId());
        return memberMapper.insertMember(memberModel);
    }
}
