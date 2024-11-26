package com.ssafy.travlog.api.mapper;

import com.ssafy.travlog.api.model.VideoInsertModel;
import com.ssafy.travlog.api.model.VideoModel;
import com.ssafy.travlog.api.model.VideoThumbnailUpdateModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoMapper {
    int insertVideo(VideoInsertModel videoInsertModel);

    int updateVideoThumbnail(VideoThumbnailUpdateModel videoThumbnailUpdateModel);

    VideoModel selectVideoByVideoId(long videoId);

    List<VideoModel> selectVideosByTravelId(long travelId);

    List<VideoModel> selectVideosByMemberId(long memberId);

    List<VideoModel> searchVideosByCoordinatesWithinDistance(@Param("coordinates") String coordinates, @Param("distance") int distance);
}
