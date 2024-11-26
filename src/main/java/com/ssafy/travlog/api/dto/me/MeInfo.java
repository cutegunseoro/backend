package com.ssafy.travlog.api.dto.me;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeInfo {
    private String publicId;
    private String displayName;
    private String bio;
    private Long currentTravelId;
}
