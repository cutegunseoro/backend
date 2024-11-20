package com.ssafy.travlog.api.service;

import com.ssafy.travlog.api.dto.MemberDto;
import com.ssafy.travlog.api.mapper.MemberMapper;
import com.ssafy.travlog.api.model.MemberModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;

    @Override
    public MemberDto getMember(long memberId) {
        MemberModel memberModel = memberMapper.selectMember(memberId);
        return new MemberDto(memberModel.getMember_id(), memberModel.getLogin_id(), memberModel.getHashed_password(), memberModel.getPublic_id(), memberModel.getDisplay_name(), memberModel.getBio(), memberModel.getCreated_at());
    }

    @Override
    public MemberDto login(String loginId, String password) {
        return null;
    }
}
