package com.ssafy.travlog.api.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushSubscriptionModel {
	private Long pushSubscriptionId;
	private Long memberId;
	private String token;
	private LocalDateTime createdAt;
	private LocalDateTime lastUsedAt;
}
