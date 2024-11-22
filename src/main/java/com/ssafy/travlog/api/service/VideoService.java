package com.ssafy.travlog.api.service;

import com.github.f4b6a3.uuid.UuidCreator;
import com.ssafy.travlog.api.dto.video.VideoMetadata;
import com.ssafy.travlog.api.dto.video.VideoMetadataUploadRequest;
import com.ssafy.travlog.api.mapper.VideoMapper;
import com.ssafy.travlog.api.model.VideoModel;
import com.ssafy.travlog.api.util.FileUtil;
import com.ssafy.travlog.api.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final String uploadDir = System.getProperty("user.dir") + "/uploads/videos";

    private final VideoMapper videoMapper;

    private final FileUtil fileUtil;
    private final MemberUtil memberUtil;

    public String uploadVideoFile(
            Authentication authenticaiton,
            MultipartFile file
    ) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is null");
        }

        // TODO: implement authentication check (rate limit, etc.)

        String filePath = uploadDir + "/" + UuidCreator.getTimeOrderedEpoch().toString() + fileUtil.getFileExtension(file.getOriginalFilename());

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
