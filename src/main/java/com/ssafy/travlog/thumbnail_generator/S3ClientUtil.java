package com.ssafy.travlog.thumbnail_generator;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class S3ClientUtil {
    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public void downloadFile(String objectKey, Path destinationPath) {
        // Build GetObjectRequest
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        try {
            // Fetch file as bytes
            ResponseBytes<GetObjectResponse> s3ObjectBytes = s3Client.getObjectAsBytes(getObjectRequest);

            // Save file to destinationPath
            File outputFile = destinationPath.toFile();
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(s3ObjectBytes.asByteArray());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while writing the file locally", e);
        }
    }

    public void uploadFile(String objectKey, Path sourcePath) {
        // Build PutObjectRequest
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        // Upload file to S3
        s3Client.putObject(putObjectRequest, RequestBody.fromFile(sourcePath));
    }
}
