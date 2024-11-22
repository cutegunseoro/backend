package com.ssafy.travlog.api.util;

import com.ssafy.travlog.api.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtil {
    private final MemberMapper memberMapper;

    public Long getMemberIdByPublicId(String publicId) {
        return memberMapper.selectMemberByPublicId(publicId).getMemberId();
    }

    public Long getMemberIdFromAuthentication(Authentication authentication) {
        return getMemberIdByPublicId(authentication.getName());
    }
}
