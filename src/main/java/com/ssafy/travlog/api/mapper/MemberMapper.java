package com.ssafy.travlog.api.mapper;

import com.ssafy.travlog.api.model.MemberModel;

public interface MemberMapper {

    MemberModel selectMemberByMemberId(long member_id);

    MemberModel selectMemberByLoginId(String login_id);

    MemberModel selectMemberByPublicId(String public_id);

    int insertMember(MemberModel memberModel);
}
