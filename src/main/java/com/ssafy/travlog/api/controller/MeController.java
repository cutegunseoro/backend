package com.ssafy.travlog.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class MeController {
//    @GetMapping
//    public ResponseEntity<MeInfoResponse> me(Authentication authentication) {
//        if (authentication == null) {
//            return ResponseEntity.status(401).build();
//        }
//
//    }
}
