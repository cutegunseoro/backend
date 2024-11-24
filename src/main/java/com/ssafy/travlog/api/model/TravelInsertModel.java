package com.ssafy.travlog.api.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelInsertModel {
	private long memberId;
	private String title;
	private LocalDateTime startDatetime;
	private LocalDateTime endDatetime;
}
