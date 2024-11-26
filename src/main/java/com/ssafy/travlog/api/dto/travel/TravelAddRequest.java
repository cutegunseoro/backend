package com.ssafy.travlog.api.dto.travel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelAddRequest {
    private String title;
    private String area;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
