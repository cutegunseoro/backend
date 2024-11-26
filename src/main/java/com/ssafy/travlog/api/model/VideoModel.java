package com.ssafy.travlog.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoModel {
    private Long videoId;
    private Long memberId;
    private Long travelId;
    private String coordinates; // Well Known Text format: POINT (longitude latitude)
    private String videoS3Key;
    private String videoContentType;
    private String thumbnailS3Url;
    private String title;
    private String description;
    private String createdAt;
}
