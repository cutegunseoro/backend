package com.ssafy.travlog.api.service;

import com.github.f4b6a3.uuid.UuidCreator;
import com.ssafy.travlog.api.dto.video.VideoFileUploadResponse;
import com.ssafy.travlog.api.dto.video.VideoMetadata;
import com.ssafy.travlog.api.dto.video.VideoMetadataUploadRequest;
import com.ssafy.travlog.api.mapper.VideoMapper;
import com.ssafy.travlog.api.model.VideoInsertModel;
import com.ssafy.travlog.api.model.VideoModel;
import com.ssafy.travlog.api.util.FileUtil;
import com.ssafy.travlog.api.util.MemberUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoMapper videoMapper;

    private final FileUtil fileUtil;
    private final MemberUtil memberUtil;

    private final String videoDir = System.getProperty("user.dir") + "/uploads/videos";

    @PostConstruct
    public void init() {
        File dir = new File(videoDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public VideoFileUploadResponse uploadVideoFile(
            Authentication authenticaiton,
            MultipartFile file
    ) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is null");
        }

        // TODO: implement authentication check (rate limit, etc.)

        String filePath = UuidCreator.getTimeOrderedEpoch().toString() + fileUtil.getFileExtension(file.getOriginalFilename());

        file.transferTo(Paths.get(videoDir, filePath).toFile());
        return new VideoFileUploadResponse(filePath);
    }

    public int uploadVideoMetadata(
            Authentication authentication,
            VideoMetadataUploadRequest videoMetadataUploadRequest
    ) {

        VideoInsertModel videoInsertModel = VideoInsertModel.builder()
                .memberId(memberUtil.getMemberIdFromAuthentication(authentication))
                .travelId(videoMetadataUploadRequest.getTravelId())
                .coordinates(videoMetadataUploadRequest.getCoordinates())
                .videoUrl(videoMetadataUploadRequest.getVideoUrl())
                .thumbnailUrl(videoMetadataUploadRequest.getThumbnailUrl())
                .title(videoMetadataUploadRequest.getTitle())
                .description(videoMetadataUploadRequest.getDescription())
                .build();
        return videoMapper.insertVideo(videoInsertModel);
    }

    public VideoMetadata getVideoMetadata(Long videoId) {
        VideoModel videoModel = videoMapper.selectVideoByVideoId(videoId);
        return convertVideoModelToVideoMetadata(videoModel);
    }

    public List<VideoMetadata> getVideoMetadataListOfTravel(Long travelId) {
        List<VideoModel> videoModelList = videoMapper.selectVideosByTravelId(travelId);
        return videoModelList.stream().map(this::convertVideoModelToVideoMetadata).toList();
    }

    public List<VideoMetadata> getVideoMetadataListOfMember(String publicId) {
        List<VideoModel> videoModelList = videoMapper.selectVideosByMemberId(memberUtil.getMemberIdByPublicId(publicId));
        return videoModelList.stream().map(this::convertVideoModelToVideoMetadata).toList();
    }

    private VideoMetadata convertVideoModelToVideoMetadata(VideoModel videoModel) {
        return VideoMetadata.builder()
                .videoId(videoModel.getVideoId())
                .publicId(memberUtil.getPublicIdByMemberId(videoModel.getMemberId()))
                .travelId(videoModel.getTravelId())
                .coordinates(videoModel.getCoordinates())
                .videoUrl(videoModel.getVideoUrl())
                .thumbnailUrl(videoModel.getThumbnailUrl())
                .title(videoModel.getTitle())
                .description(videoModel.getDescription())
                .createdAt(videoModel.getCreatedAt())
                .build();
    }
}
