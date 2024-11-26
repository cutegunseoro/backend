package com.ssafy.travlog.notification_server;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Component
public class FirebaseInitializer {
	@PostConstruct
	public void init() throws IOException {
		try (FileInputStream serviceAccount = new FileInputStream(
			Path.of(System.getProperty("user.dir"), "firebase-service-account.json").toString());) {
			FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();
			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}
		}
	}
}
