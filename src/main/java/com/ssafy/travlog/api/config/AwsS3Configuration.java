package com.ssafy.travlog.api.config;

import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.profiles.ProfileFile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsS3Configuration {
	@Bean
	public S3Client s3Client() {
		return S3Client.builder()
			.region(Region.AP_NORTHEAST_2)
			.credentialsProvider(ProfileCredentialsProvider.builder()
				.profileFile(ProfileFile.builder()
					.type(ProfileFile.Type.CREDENTIALS)
					.content(Paths.get(System.getProperty("user.dir"), "credentials"))
					.build()
				)
				.build()
			)
			.build();
	}
}
