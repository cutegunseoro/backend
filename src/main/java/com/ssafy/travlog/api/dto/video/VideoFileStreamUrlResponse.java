package com.ssafy.travlog.api.dto.video;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoFileStreamUrlResponse {
    private String contentType;
    private URL preSignedUrl;
}
