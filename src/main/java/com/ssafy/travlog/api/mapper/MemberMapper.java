package com.ssafy.travlog.api.mapper;

import com.ssafy.travlog.api.model.MemberInsertModel;
import com.ssafy.travlog.api.model.MemberModel;

public interface MemberMapper {

    MemberModel selectMemberByMemberId(long memberId);

    MemberModel selectMemberByLoginId(String loginId);

    MemberModel selectMemberByPublicId(String publicId);

    int insertMember(MemberInsertModel memberInsertModel);
}
