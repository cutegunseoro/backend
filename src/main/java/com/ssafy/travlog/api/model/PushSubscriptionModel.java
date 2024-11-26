package com.ssafy.travlog.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushSubscriptionModel {
    private Long pushSubscriptionId;
    private Long memberId;
    private String endpoint;
    private String p256dh;
    private String auth;
    private LocalDateTime createdAt;
    private LocalDateTime lastUsedAt;
}
