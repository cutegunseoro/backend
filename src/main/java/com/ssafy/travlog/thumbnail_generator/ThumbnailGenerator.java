package com.ssafy.travlog.thumbnail_generator;

import com.ssafy.travlog.api.mapper.VideoMapper;
import com.ssafy.travlog.api.model.VideoModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.FileChannelWrapper;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
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

    private final Path videoDir = Path.of(System.getProperty("user.dir"), "videos");
    private final Path thumbnailDir = Path.of(System.getProperty("user.dir"), "thumbnails");

    public void generateThumbnail(long videoId) {
        VideoModel videoModel = videoMapper.selectVideoByVideoId(videoId);
        String videoS3Key = videoModel.getVideoS3Key();
        String objectKey = videoS3Key.split("/")[1];
        Path videoPath = videoDir.resolve(objectKey);
        s3ClientUtil.downloadFile(videoModel.getVideoS3Key(), videoPath);

        // Generate thumbnail
        try (FileChannelWrapper channel = NIOUtils.readableFileChannel(videoPath.toString())) {
            FrameGrab grab = FrameGrab.createFrameGrab(channel);

            // Seek to a specific frame (1st second, or another frame index)
            grab.seekToFramePrecise(25);
            Picture picture = grab.getNativeFrame();

            // Convert to BufferedImage
            BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
            ImageIO.write(bufferedImage, "jpg", thumbnailDir.resolve(objectKey + ".jpg").toFile());
        } catch (JCodecException | IOException e) {
            log.error("Failed to generate thumbnail", e);
            throw new RuntimeException(e);
        }
    }
}
