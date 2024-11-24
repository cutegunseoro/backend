package com.ssafy.travlog.api.dto.travel;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelInfo {
	private Long travelId;
	private String publicId;
	private String title;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private LocalDateTime createdAt;
}
