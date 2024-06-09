package com.example.shopping.config;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path}")
    private String firebaseConfigPath;

    @Bean
    public CommandLineRunner initializeFirebase() {
        return args -> {
            try {
                if (FirebaseApp.getApps().isEmpty()) {  // FirebaseApp이 초기화되지 않은 경우에만 초기화
                    FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath);
                    FirebaseOptions options = new FirebaseOptions.Builder()
                            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                            .build();
                    FirebaseApp.initializeApp(options);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}