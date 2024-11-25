package com.ssafy.travlog.api.service;

import java.net.URL;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ssafy.travlog.api.dto.video.GenerateVideoFileUploadUrlResponse;
import com.ssafy.travlog.api.dto.video.VideoMetadata;
import com.ssafy.travlog.api.dto.video.VideoMetadataListResponse;
import com.ssafy.travlog.api.dto.video.VideoMetadataResponse;
import com.ssafy.travlog.api.dto.video.VideoMetadataUploadRequest;
import com.ssafy.travlog.api.mapper.VideoMapper;
import com.ssafy.travlog.api.model.VideoInsertModel;
import com.ssafy.travlog.api.model.VideoModel;
import com.ssafy.travlog.api.util.MemberUtil;
import com.ssafy.travlog.api.util.S3Util;
import com.ssafy.travlog.api.util.UuidUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoService {
	private final VideoMapper videoMapper;

	private final S3Util s3Util;
	private final MemberUtil memberUtil;
	private final UuidUtil uuidUtil;

	private final List<String> allowedContentTypes = List.of("video/webm", "video/mp4");

	public GenerateVideoFileUploadUrlResponse generateVideoFileUploadUrl(
		Authentication authentication,
		String contentType
	) {
		if (!allowedContentTypes.contains(contentType)) {
			throw new RuntimeException("Not supported Video Type");
		}
		URL preSignedUrl = s3Util.generatePreSignedUrl(uuidUtil.getUUIDv7().toString());
		return new GenerateVideoFileUploadUrlResponse(preSignedUrl);
	}

	public void uploadVideoMetadata(
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
		int result = videoMapper.insertVideo(videoInsertModel);
		if (result != 1) {
			throw new RuntimeException("Failed to insert video metadata");
		}
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
			.videoUrl(videoModel.getVideoUrl())
			.thumbnailUrl(videoModel.getThumbnailUrl())
			.title(videoModel.getTitle())
			.description(videoModel.getDescription())
			.createdAt(videoModel.getCreatedAt())
			.build();
	}
}
