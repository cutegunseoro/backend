package com.ssafy.travlog.api.dto.travel;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelAddRequest {
	private String publicId;
	private String title;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
}
