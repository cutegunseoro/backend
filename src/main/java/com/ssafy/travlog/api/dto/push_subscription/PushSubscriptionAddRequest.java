package com.ssafy.travlog.api.dto.push_subscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushSubscriptionAddRequest {
	private String token;
}
