package com.ssafy.travlog.api.mapper;

import java.util.List;

import com.ssafy.travlog.api.model.TravelInsertModel;
import com.ssafy.travlog.api.model.TravelModel;

public interface TravelMapper {
	int insertTravel(TravelInsertModel travelInsertModel);

	TravelModel selectTravelByTravelId(int travelId);

	List<TravelModel> selectTravelsByMemberId(int memberId);
}
