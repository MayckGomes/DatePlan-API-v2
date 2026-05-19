package com.mayckgomes.dateplan_api;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;

@SpringBootApplication
public class DateplanApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(DateplanApiApplication.class, args);

		try {

			Dotenv dotenv = Dotenv.load();

			String path = dotenv.get("FIREBASE_JSON_PATH");

			FileInputStream serviceAccount =
					new FileInputStream(path);

			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.build();

			FirebaseApp.initializeApp(options);
			System.out.println("ok");

		} catch (Exception e) {
			System.out.println("firebase error");
		}
	}

}
