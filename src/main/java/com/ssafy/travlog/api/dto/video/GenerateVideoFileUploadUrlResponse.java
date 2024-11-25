package com.ssafy.travlog.api.dto.video;

import java.net.URL;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateVideoFileUploadUrlResponse {
	private String objectKey;
	private URL preSignedUrl;
}
