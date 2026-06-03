package com.mayckgomes.dateplan_api;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;

@SpringBootApplication
public class DateplanApiApplication {

	@Value("${firebase.json.path}")
	private static String path;

	public static void main(String[] args) {

		SpringApplication.run(DateplanApiApplication.class, args);

		try {

			FileInputStream serviceAccount =
					new FileInputStream(path);

			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.build();

			FirebaseApp.initializeApp(options);
			System.out.println("firebase initialized successfully ");

		} catch (Exception e) {
			System.out.println("firebase error");
		}
	}

}
