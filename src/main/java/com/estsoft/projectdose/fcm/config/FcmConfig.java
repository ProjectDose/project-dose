package com.estsoft.projectdose.fcm.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.estsoft.projectdose.exception.BaseCustomException;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import jakarta.annotation.PostConstruct;

@Configuration
public class FcmConfig {

	private static final String SECRET_FILE_PATH = "firebase/firebase-key.json";
	private static final String SECRET_FILE_NOT_FOUND = "Secret file not found.";
	private static final String INVALID_SECRET_FILE = "Invalid secret file.";

	@PostConstruct
	public void initialize() {
		try {
			Resource resource = new ClassPathResource(SECRET_FILE_PATH);

			if (!resource.exists()) {
				throw new BaseCustomException(SECRET_FILE_NOT_FOUND);
			}

			try (InputStream serviceAccount = resource.getInputStream()) {
				GoogleCredentials googleCredentials = GoogleCredentials.fromStream(serviceAccount);

				FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(googleCredentials)
					.build();

				if (FirebaseApp.getApps().isEmpty()) {
					FirebaseApp.initializeApp(options);
					System.out.println("Firebase initialized successfully.");
				} else {
					System.out.println("Firebase already initialized.");
				}
			}
		} catch (IOException e) {
			throw new BaseCustomException(INVALID_SECRET_FILE, e);
		}
	}
}

