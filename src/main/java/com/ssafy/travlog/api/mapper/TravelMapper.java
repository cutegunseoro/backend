package com.ssafy.travlog.api.mapper;

import com.ssafy.travlog.api.model.TravelInsertModel;
import com.ssafy.travlog.api.model.TravelModel;

import java.util.List;

public interface TravelMapper {
    int insertTravel(TravelInsertModel travelInsertModel);

    TravelModel selectTravelByTravelId(long travelId);

    List<TravelModel> selectTravelsByMemberId(long memberId);
}
