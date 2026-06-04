package com.mayckgomes.dateplan_api.configurations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import java.io.InputStream;

@Configuration
public class FirebaseConfiguration {

    @Value("${firebase.json.path}")
    private Resource firebaseResource;

    @Bean
    public FirebaseApp firebaseApp() {

        if (FirebaseApp.getApps().isEmpty()) {

            try (InputStream serviceAccount = firebaseResource.getInputStream()) {

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                System.out.println("Firebase initialized successfully");

                return FirebaseApp.initializeApp(options);

            } catch (Exception e) {

                System.out.println("Firebase Error");

            }
        }

        return FirebaseApp.getInstance();
    }
}
