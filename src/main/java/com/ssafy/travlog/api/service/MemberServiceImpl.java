package com.ssafy.travlog.api.service;

import com.ssafy.travlog.api.dto.LoginRequest;
import com.ssafy.travlog.api.dto.MemberDto;
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
    public MemberDto getMemberByMemberId(long memberId) {
        MemberModel memberModel = memberMapper.selectMemberByMemberId(memberId);
        return new MemberDto(memberModel.getMember_id(), memberModel.getLogin_id(), memberModel.getHashed_password(), memberModel.getPublic_id(), memberModel.getDisplay_name(), memberModel.getBio(), memberModel.getCreated_at());
    }

    @Override
    public MemberDto getMemberByLoginId(String loginId) {
        MemberModel memberModel = memberMapper.selectMemberByLoginId(loginId);
        return new MemberDto(memberModel.getMember_id(), memberModel.getLogin_id(), memberModel.getHashed_password(), memberModel.getPublic_id(), memberModel.getDisplay_name(), memberModel.getBio(), memberModel.getCreated_at());
    }

    @Override
    public MemberDto getMemberByPublicId(String publicId) {
        MemberModel memberModel = memberMapper.selectMemberByPublicId(publicId);
        return new MemberDto(memberModel.getMember_id(), memberModel.getLogin_id(), memberModel.getHashed_password(), memberModel.getPublic_id(), memberModel.getDisplay_name(), memberModel.getBio(), memberModel.getCreated_at());
    }

    @Override
    public MemberDto login(LoginRequest loginRequest) {
        MemberModel memberModel = memberMapper.selectMemberByLoginId(loginRequest.getLoginId());
        if (passwordEncoder.matches(loginRequest.getPassword(), memberModel.getHashed_password())) {
            return new MemberDto(memberModel.getMember_id(), memberModel.getLogin_id(), memberModel.getHashed_password(), memberModel.getPublic_id(), memberModel.getDisplay_name(), memberModel.getBio(), memberModel.getCreated_at());
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
