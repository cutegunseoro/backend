package com.ssafy.travlog.api.service;

import com.ssafy.travlog.api.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeService {
    private final MemberService memberService;
    private final TravelService travelService;

    private final MemberUtil memberUtil;

//    public MeInfoResponse getMeInfo(Authentication authentication) {
//        Long memberId = memberUtil.getMemberIdFromAuthentication(authentication);
//        MemberInfoResponse memberInfoResponse = memberService.getMemberByMemberId(memberId);
//        TravelInfoListResponse travelInfoListResponse = travelService.getTravelInfoListOfMember(memberInfoResponse.getMemberInfo().getPublicId());
//        return new MeInfoResponse(memberInfoResponse, travelInfoListResponse);
//    }
}
