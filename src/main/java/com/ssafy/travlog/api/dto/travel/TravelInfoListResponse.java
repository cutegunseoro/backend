package com.ssafy.travlog.api.dto.travel;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelInfoListResponse {
	private List<TravelInfo> travels;
}
