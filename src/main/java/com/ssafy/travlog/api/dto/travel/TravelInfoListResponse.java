package com.ssafy.travlog.api.dto.travel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelInfoListResponse {
    private List<TravelInfo> travels;
}
