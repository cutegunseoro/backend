package com.ssafy.travlog.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelInsertModel {
    private long memberId;
    private String title;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
