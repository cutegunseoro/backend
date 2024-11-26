package com.ssafy.travlog.api.service;

import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ssafy.travlog.api.dto.me.MeInfo;
import com.ssafy.travlog.api.dto.me.MeInfoResponse;
import com.ssafy.travlog.api.mapper.MemberMapper;
import com.ssafy.travlog.api.mapper.TravelMapper;
import com.ssafy.travlog.api.model.MemberModel;
import com.ssafy.travlog.api.model.TravelModel;
import com.ssafy.travlog.api.util.MemberUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MeService {
	private final MemberMapper memberMapper;
	private final TravelMapper travelMapper;

	private final MemberUtil memberUtil;

	public MeInfoResponse getMeInfo(Authentication authentication) {
		String publicId = authentication.getName();
		Long memberId = memberUtil.getMemberIdByPublicId(publicId);

		MemberModel memberModel = memberMapper.selectMemberByMemberId(memberId);
		TravelModel travelModel = travelMapper.selectLastTravelByMemberId(memberId);

		LocalDateTime now = LocalDateTime.now();

		MeInfo meInfo = MeInfo.builder()
			.publicId(publicId)
			.displayName(memberModel.getDisplayName())
			.bio(memberModel.getBio())
			.currentTravelId(travelModel == null
				|| travelModel.getStartDateTime().isAfter(now) || travelModel.getEndDateTime().isBefore(now)
				? null : travelModel.getTravelId())
			.build();
		return new MeInfoResponse(meInfo);
	}
}
