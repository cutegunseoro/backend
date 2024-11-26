package com.ssafy.travlog.thumbnail_generator;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThumbnailTaskProcessor {
    private final ThumbnailTaskQueue taskQueue;
    @Qualifier("thumbnailTaskExecutor")
    private final TaskExecutor taskExecutor;
    private final ThumbnailGenerator thumbnailGenerator;

    @PostConstruct
    public void startProcessing() {
        // Start a thread to continuously poll the queue and process tasks
        Thread queueProcessorThread = new Thread(() -> {
            while (true) {
                try {
                    ThumbnailTask task = taskQueue.dequeue(); // Blocks until a task is available
                    taskExecutor.execute(() -> {
                        try {
                            thumbnailGenerator.generateThumbnail(task.getVideoId()); // Process the task
                        } catch (Exception e) {
                            log.warn(e.toString());
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        queueProcessorThread.setDaemon(true); // Daemon thread so it doesn't block application shutdown
        queueProcessorThread.start();
    }
}
