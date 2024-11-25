package com.ssafy.travlog.api.util;

import java.net.URL;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Component
@RequiredArgsConstructor
public class S3Util {
	private final S3Client s3Client;
	private final S3Presigner s3Presigner = S3Presigner.create();

	@Value("${aws.s3.bucket-name}")
	private String bucketName;
	@Value("${aws.s3.link-expiration-minute}")
	private int linkExpirationMinute;

	public URL generatePreSignedPutUrl(String objectKey, String contentType) {
		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
			.bucket(bucketName)
			.key(objectKey)
			.contentType(contentType)
			.build();

		PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
			.putObjectRequest(putObjectRequest)
			.signatureDuration(Duration.ofMinutes(linkExpirationMinute))
			.build();

		return s3Presigner.presignPutObject(putObjectPresignRequest).url();
	}

	public URL generatePreSignedPutUrl(String objectKey) {
		return generatePreSignedPutUrl(objectKey, null);
	}

	public URL generatePreSignedGetUrl(String objectKey) {
		GetObjectRequest getObjectRequest = GetObjectRequest.builder()
			.bucket(bucketName)
			.key(objectKey)
			.build();

		GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
			.getObjectRequest(getObjectRequest)
			.signatureDuration(Duration.ofMinutes(linkExpirationMinute))
			.build();

		return s3Presigner.presignGetObject(getObjectPresignRequest).url();
	}
}