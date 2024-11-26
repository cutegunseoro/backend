package com.ssafy.travlog.api.controller;

import com.ssafy.travlog.api.dto.travel.TravelAddRequest;
import com.ssafy.travlog.api.dto.travel.TravelInfoResponse;
import com.ssafy.travlog.api.dto.video.VideoMetadataListResponse;
import com.ssafy.travlog.api.service.TravelService;
import com.ssafy.travlog.api.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/travels")
@RequiredArgsConstructor
public class TravelController {
    private final TravelService travelService;
    private final VideoService videoService;

    @PostMapping
    public ResponseEntity<Void> addTravel(
            Authentication authentication,
            @RequestBody TravelAddRequest travelAddRequest
    ) {
        travelService.addTravel(authentication, travelAddRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{travelId}")
    public ResponseEntity<TravelInfoResponse> getTravelInfo(@PathVariable Long travelId) {
        var res = travelService.getTravelInfo(travelId);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/{travelId}/videos")
    public ResponseEntity<VideoMetadataListResponse> getVideoMetadataListOfTravel(@PathVariable Long travelId) {
        var res = videoService.getVideoMetadataListOfTravel(travelId);
        return ResponseEntity.ok().body(res);
    }
}
