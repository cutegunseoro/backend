package com.ssafy.travlog.api.dto.me;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeInfo {
	private String publicId;
	private String displayName;
	private String bio;
	private Long currentTravelId;
	private LocalDateTime currentTravelEndDateTime;
}
