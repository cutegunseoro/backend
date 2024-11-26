package com.ssafy.travlog.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PushSubscriptionInsertModel {
    private Long memberId;
    private String endpoint;
    private String p256dh;
    private String auth;
}
