package com.ssafy.travlog.api.controller;

import com.ssafy.travlog.api.dto.push_subscription.PushSubscriptionAddRequest;
import com.ssafy.travlog.api.service.PushSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/push-subscriptions")
@RequiredArgsConstructor
public class PushSubscriptionController {
    private final PushSubscriptionService pushSubscriptionService;

    @PutMapping
    public ResponseEntity<Void> addPushSubscription(
            Authentication authentication,
            PushSubscriptionAddRequest request
    ) {
        pushSubscriptionService.addPushSubscription(authentication, request);
        return ResponseEntity.ok().build();
    }
}
