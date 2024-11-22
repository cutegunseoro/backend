package com.ssafy.travlog.api.service;

import com.github.f4b6a3.uuid.UuidCreator;
import com.ssafy.travlog.api.dto.video.VideoMetaInsertRequest;
import com.ssafy.travlog.api.mapper.VideoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final String uploadDir = System.getProperty("user.dir") + "/uploads/videos";

    private final VideoMapper videoMapper;

    public String saveVideo(MultipartFile file) throws IOException {
        String filePath = uploadDir + "/" + UuidCreator.getTimeOrderedEpoch().toString() + getFileExtension(file.getOriginalFilename());

        file.transferTo(new File(filePath));
        return filePath;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex >= 0) ? fileName.substring(dotIndex) : ""; // Include the dot (e.g., ".mp4")
    }

    public int uploadVideoMetadata(VideoMetaInsertRequest videoMetaInsertRequest) {
        return videoMapper.insertVideo(videoMetaInsertRequest);
    }
}
