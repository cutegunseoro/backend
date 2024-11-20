package com.ssafy.travlog.api.service;

import com.ssafy.travlog.api.dto.MemberDto;

public interface MemberService {

    MemberDto getMember(long memberId);

    MemberDto login(String loginId, String password);
}
