package com.ssafy.travlog.api.service;

import org.springframework.stereotype.Service;

import com.ssafy.travlog.api.dto.travel.TravelAddRequest;
import com.ssafy.travlog.api.mapper.TravelMapper;
import com.ssafy.travlog.api.model.TravelInsertModel;
import com.ssafy.travlog.api.util.MemberUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelService {
	private final TravelMapper travelMapper;

	private final MemberUtil memberUtil;

	public void addTravel(TravelAddRequest travelAddRequest) {
		TravelInsertModel travelInsertModel = TravelInsertModel.builder()
			.memberId(memberUtil.getMemberIdByPublicId(travelAddRequest.getPublicId()))
			.title(travelAddRequest.getTitle())
			.startDateTime(travelAddRequest.getStartDateTime())
			.endDateTime(travelAddRequest.getEndDateTime())
			.build();
		travelMapper.insertTravel(travelInsertModel);
	}
}
