package com.ssafy.travlog.api.mapper;

import com.ssafy.travlog.api.dto.video.VideoMetadataUploadRequest;
import com.ssafy.travlog.api.model.VideoModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoMapper {
    int insertVideo(VideoMetadataUploadRequest videoMetadataUploadRequest);

    VideoModel selectVideoByVideoId(long videoId);

    List<VideoModel> selectVideosByTravelId(long travelId);

    List<VideoModel> selectVideosByMemberId(long memberId);

    List<VideoModel> searchVideosByCoordinatesWithinDistance(@Param("coordinates") String coordinates, @Param("distance") int distance);
}
