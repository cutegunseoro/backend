package com.ssafy.travlog.api.dto.video;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoMetadataUploadRequest {
    private Long memberId;
    private Long travelId;
    private String coordinates;
    private String videoUrl;
    private String thumbnailUrl;
    private String title;
    private String description;
}
