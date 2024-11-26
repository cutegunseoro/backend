package com.ssafy.travlog.thumbnail_generator;

import com.ssafy.travlog.api.mapper.VideoMapper;
import com.ssafy.travlog.api.model.VideoModel;
import com.ssafy.travlog.api.model.VideoThumbnailUpdateModel;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

@Slf4j
@Component
@RequiredArgsConstructor
public class ThumbnailGenerator {
    private final VideoMapper videoMapper;
    private final S3ClientUtil s3ClientUtil;

    private final Java2DFrameConverter converter = new Java2DFrameConverter();
    private final Path videoDir = Path.of(System.getProperty("user.dir"), "videos");
    private final Path thumbnailDir = Path.of(System.getProperty("user.dir"), "thumbnails");

    @PostConstruct
    public void init() {
        if (!videoDir.toFile().exists()) {
            videoDir.toFile().mkdirs();
        }
        if (!thumbnailDir.toFile().exists()) {
            thumbnailDir.toFile().mkdirs();
        }
    }

    public void generateThumbnail(long videoId) {
        VideoModel videoModel = videoMapper.selectVideoByVideoId(videoId);
        String videoS3Key = videoModel.getVideoS3Key();
        String objectKey = videoS3Key.split("/")[1];
        Path videoPath = videoDir.resolve(objectKey);
        s3ClientUtil.downloadFile(videoModel.getVideoS3Key(), videoPath);

        Path thumbnailPath = thumbnailDir.resolve(objectKey + ".jpg");
        try {
            generateThumbnailFromVideo(videoPath, thumbnailPath);

            // Upload thumbnail to S3
            String thumbnailS3Key = "thumbnails/" + objectKey + ".jpg";
            s3ClientUtil.uploadFile(thumbnailS3Key, thumbnailPath);

            // write thumbnailS3Url to database
            String thumbnailS3Url = s3ClientUtil.getPublicUrl("ap-northeast-2", s3ClientUtil.getBucketName(), thumbnailS3Key);
            videoMapper.updateVideoThumbnail(new VideoThumbnailUpdateModel(videoId, thumbnailS3Url));
        } catch (IOException e) {
            log.error("Failed to generate thumbnail from video", e);
            throw new RuntimeException(e);
        }
    }

    private void generateThumbnailFromVideo(Path videoPath, Path thumbnailPath) throws IOException {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath.toFile())) {
            grabber.start();
            grabber.setFrameNumber(5);
            Frame frame = grabber.grabImage();
            if (frame == null) {
                throw new RuntimeException("Failed to grab frame");
            }
            BufferedImage image = converter.convert(frame);
            ImageIO.write(image, "jpg", thumbnailPath.toFile());
            grabber.stop();
        }
    }
}
