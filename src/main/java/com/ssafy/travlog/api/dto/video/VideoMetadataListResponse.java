package com.ssafy.travlog.api.dto.video;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoMetadataListResponse {
    private List<VideoMetadata> videos;

    // TODO: implement pagination
}
