package com.ssafy.travlog.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.travlog.api.dto.travel.TravelAddRequest;
import com.ssafy.travlog.api.dto.travel.TravelInfo;
import com.ssafy.travlog.api.dto.travel.TravelInfoListResponse;
import com.ssafy.travlog.api.dto.travel.TravelInfoResponse;
import com.ssafy.travlog.api.mapper.TravelMapper;
import com.ssafy.travlog.api.model.TravelInsertModel;
import com.ssafy.travlog.api.model.TravelModel;
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
		int result = travelMapper.insertTravel(travelInsertModel);
		if (result != 1) {
			throw new RuntimeException("failed to add travel");
		}
	}

	public TravelInfoResponse getTravelInfo(long travelId) {
		TravelModel travelModel = travelMapper.selectTravelByTravelId(travelId);
		TravelInfo travelInfo = convertTravelModelToTravelInfo(travelModel);
		return new TravelInfoResponse(travelInfo);
	}

	public TravelInfoListResponse getTravelInfoListOfMember(String publicId) {
		long memberId = memberUtil.getMemberIdByPublicId(publicId);
		List<TravelInfo> travelInfoList = travelMapper.selectTravelsByMemberId(memberId)
			.stream()
			.map(this::convertTravelModelToTravelInfo)
			.toList();
		return new TravelInfoListResponse(travelInfoList);
	}

	private TravelInfo convertTravelModelToTravelInfo(TravelModel travelModel) {
		return TravelInfo.builder()
			.travelId(travelModel.getTravelId())
			.publicId(memberUtil.getPublicIdByMemberId(travelModel.getMemberId()))
			.title(travelModel.getTitle())
			.startDateTime(travelModel.getStartDateTime())
			.endDateTime(travelModel.getEndDateTime())
			.createdAt(travelModel.getCreatedAt())
			.build();
	}
}
