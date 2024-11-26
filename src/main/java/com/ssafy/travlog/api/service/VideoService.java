package com.ssafy.travlog.api.service;

import com.ssafy.travlog.api.dto.video.*;
import com.ssafy.travlog.api.mapper.VideoMapper;
import com.ssafy.travlog.api.model.VideoInsertModel;
import com.ssafy.travlog.api.model.VideoModel;
import com.ssafy.travlog.api.util.MemberUtil;
import com.ssafy.travlog.api.util.S3PreSignUtil;
import com.ssafy.travlog.api.util.UuidUtil;
import com.ssafy.travlog.thumbnail_generator.ThumbnailTask;
import com.ssafy.travlog.thumbnail_generator.ThumbnailTaskQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoMapper videoMapper;

    private final S3PreSignUtil s3PreSignUtil;
    private final MemberUtil memberUtil;
    private final UuidUtil uuidUtil;

    private final ThumbnailTaskQueue thumbnailTaskQueue;

    private final List<String> allowedContentTypes = List.of("video/webm", "video/mp4");

    public VideoFileUploadUrlResponse generateVideoFileUploadUrl(
            Authentication authentication,
            String contentType
    ) {
        // TODO: add some duplicated request if necessary
        if (!allowedContentTypes.contains(contentType)) {
            throw new RuntimeException("Not supported Video Type");
        }
        String objectKey = "videos/" + uuidUtil.getUUIDv7().toString();
        URL preSignedUrl = s3PreSignUtil.generatePreSignedPutUrl(objectKey, "application/octet-stream");
        return new VideoFileUploadUrlResponse(objectKey, preSignedUrl);
    }

    public VideoFileStreamUrlResponse generateVideoFileStreamUrl(
            Authentication authentication,
            Long videoId
    ) {
        VideoModel videoModel = videoMapper.selectVideoByVideoId(videoId);
        // TODO: add some authentication if it's private video
        URL preSignedUrl = s3PreSignUtil.generatePreSignedGetUrl(videoModel.getVideoS3Key());
        return new VideoFileStreamUrlResponse(videoModel.getVideoContentType(), preSignedUrl);
    }

    public void uploadVideoMetadata(
            Authentication authentication,
            VideoMetadataUploadRequest videoMetadataUploadRequest
    ) {
        VideoInsertModel videoInsertModel = VideoInsertModel.builder()
                .memberId(memberUtil.getMemberIdFromAuthentication(authentication))
                .travelId(videoMetadataUploadRequest.getTravelId())
                .coordinates(videoMetadataUploadRequest.getCoordinates())
                .videoS3Key(videoMetadataUploadRequest.getVideoS3Key())
                .videoContentType(videoMetadataUploadRequest.getVideoContentType())
                .title(videoMetadataUploadRequest.getTitle())
                .description(videoMetadataUploadRequest.getDescription())
                .build();
        int result = videoMapper.insertVideo(videoInsertModel);
        if (result != 1) {
            throw new RuntimeException("Failed to insert video metadata");
        }
        thumbnailTaskQueue.enqueue(new ThumbnailTask(videoInsertModel.getVideoId()));
    }

    public VideoMetadataResponse getVideoMetadata(Long videoId) {
        VideoModel videoModel = videoMapper.selectVideoByVideoId(videoId);
        VideoMetadata videoMetadata = convertVideoModelToVideoMetadata(videoModel);
        return new VideoMetadataResponse(videoMetadata);
    }

    public VideoMetadataListResponse getVideoMetadataListOfTravel(Long travelId) {
        List<VideoModel> videoModelList = videoMapper.selectVideosByTravelId(travelId);
        List<VideoMetadata> videoMetadataList = videoModelList.stream()
                .map(this::convertVideoModelToVideoMetadata)
                .toList();
        return new VideoMetadataListResponse(videoMetadataList);
    }

    public VideoMetadataListResponse getVideoMetadataListOfMember(String publicId) {
        List<VideoModel> videoModelList = videoMapper.selectVideosByMemberId(
                memberUtil.getMemberIdByPublicId(publicId));
        List<VideoMetadata> videoMetadataList = videoModelList.stream()
                .map(this::convertVideoModelToVideoMetadata)
                .toList();
        return new VideoMetadataListResponse(videoMetadataList);
    }

    public VideoMetadataListResponse getVideoMetadataListByLocationAndDistance(String coordinates, Integer distance) {
        if (distance == null) {
            distance = 10;
        }
        List<VideoModel> videoModelList = videoMapper.searchVideosByCoordinatesWithinDistance(coordinates, distance);
        List<VideoMetadata> videoMetadataList = videoModelList.stream()
                .map(this::convertVideoModelToVideoMetadata)
                .toList();
        return new VideoMetadataListResponse(videoMetadataList);
    }

    private VideoMetadata convertVideoModelToVideoMetadata(VideoModel videoModel) {
        return VideoMetadata.builder()
                .videoId(videoModel.getVideoId())
                .publicId(memberUtil.getPublicIdByMemberId(videoModel.getMemberId()))
                .travelId(videoModel.getTravelId())
                .coordinates(videoModel.getCoordinates())
                .videoS3Key(videoModel.getVideoS3Key())
                .videoContentType(videoModel.getVideoContentType())
                .thumbnailS3Url(videoModel.getThumbnailS3Url())
                .title(videoModel.getTitle())
                .description(videoModel.getDescription())
                .createdAt(videoModel.getCreatedAt())
                .build();
    }
}
