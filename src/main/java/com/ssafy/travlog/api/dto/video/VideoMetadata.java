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
	private String videoS3Key;
	private String thumbnailS3Key;
	private String videoContentType;
	private String thumbnailContentType;
	private String title;
	private String description;
	private String createdAt;
}
