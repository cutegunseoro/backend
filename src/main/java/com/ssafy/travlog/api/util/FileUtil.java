package com.ssafy.travlog.api.util;

import org.springframework.stereotype.Component;

@Component
public class FileUtil {
    public String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex >= 0) ? fileName.substring(dotIndex) : ""; // Include the dot (e.g., ".mp4")
    }
}
