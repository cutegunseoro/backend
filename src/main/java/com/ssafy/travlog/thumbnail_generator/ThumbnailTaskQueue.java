package com.ssafy.travlog.thumbnail_generator;

import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class ThumbnailTaskQueue {
    private final BlockingQueue<ThumbnailTask> taskQueue = new LinkedBlockingQueue<>();

    public void enqueue(ThumbnailTask task) {
        taskQueue.add(task);
    }

    public ThumbnailTask dequeue() {
        try {
            return taskQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
