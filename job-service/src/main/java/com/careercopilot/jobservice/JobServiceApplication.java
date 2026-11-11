package com.careercopilot.jobservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class JobServiceApplication {

    public static void main(String[] args) {
        loadDotenv();
        SpringApplication.run(JobServiceApplication.class, args);
    }

    private static void loadDotenv() {
        String[] possiblePaths = {".env", "../.env"};
        for (String pathStr : possiblePaths) {
            File envFile = new File(pathStr);
            if (envFile.exists()) {
                try {
                    List<String> lines = Files.readAllLines(Paths.get(pathStr));
                    for (String line : lines) {
                        line = line.trim();
                        if (line.isEmpty() || line.startsWith("#") || !line.contains("=")) {
                            continue;
                        }
                        String[] parts = line.split("=", 2);
                        String key = parts[0].trim();
                        String value = parts[1].trim();
                        if (System.getProperty(key) == null && System.getenv(key) == null) {
                            System.setProperty(key, value);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Failed to parse .env file from path: " + pathStr + " -> " + e.getMessage());
                }
            }
        }
    }
}