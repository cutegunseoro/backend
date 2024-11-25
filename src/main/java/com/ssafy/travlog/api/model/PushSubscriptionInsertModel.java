package com.ssafy.travlog.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushSubscriptionInsertModel {
	private Long memberId;
	private String endpoint;
	private String p256dh;
	private String auth;
}
