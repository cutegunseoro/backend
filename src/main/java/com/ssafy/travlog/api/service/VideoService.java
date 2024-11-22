package com.ssafy.travlog.api.service;

import com.github.f4b6a3.uuid.UuidCreator;
import com.ssafy.travlog.api.dto.video.VideoMetadataUploadRequest;
import com.ssafy.travlog.api.mapper.VideoMapper;
import com.ssafy.travlog.api.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final String uploadDir = System.getProperty("user.dir") + "/uploads/videos";

    private final VideoMapper videoMapper;

    private final MemberUtil memberUtil;

    public String uploadVideoFile(
            Authentication authenticaiton,
            MultipartFile file
    ) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is null");
        }

        // TODO: implement authentication check (rate limit, etc.)

        String filePath = uploadDir + "/" + UuidCreator.getTimeOrderedEpoch().toString() + getFileExtension(file.getOriginalFilename());

        file.transferTo(new File(filePath));
        return filePath;
    }

    public int uploadVideoMetadata(
            Authentication authentication,
            VideoMetadataUploadRequest videoMetadataUploadRequest
    ) {
        Long memberId = memberUtil.getMemberIdFromAuthentication(authentication);
        videoMetadataUploadRequest.setMemberId(memberId);

        return videoMapper.insertVideo(videoMetadataUploadRequest);
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex >= 0) ? fileName.substring(dotIndex) : ""; // Include the dot (e.g., ".mp4")
    }
}
