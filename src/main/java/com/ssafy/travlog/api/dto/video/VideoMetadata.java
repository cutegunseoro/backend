package com.ssafy.travlog.api.dto.video;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoMetadata {
    private Long videoId;
    private String publicId;
    private Long travelId;
    private String coordinates; // Well Known Text format: POINT (longitude latitude)
    private String videoUrl;
    private String thumbnailUrl;
    private String title;
    private String description;
    private String createdAt;
}
