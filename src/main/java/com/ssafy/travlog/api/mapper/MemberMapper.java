package com.ssafy.travlog.api.mapper;

import com.ssafy.travlog.api.model.MemberModel;

public interface MemberMapper {

    MemberModel selectMember(long member_id);
}
