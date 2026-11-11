package com.careercopilot.resume_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ResumeServiceApplication {

	public static void main(String[] args) {
		loadDotenv();
		SpringApplication.run(ResumeServiceApplication.class, args);
	}

	private static void loadDotenv() {
		File[] candidates = new File[]{ new File(".env"), new File("../.env") };
		for (File file : candidates) {
			if (file.exists() && file.isFile()) {
				try (InputStream input = new FileInputStream(file)) {
					Properties props = new Properties();
					props.load(input);
					props.forEach((k, v) -> {
						String val = v.toString().trim();
						if (val.startsWith("\"") && val.endsWith("\"") && val.length() >= 2) {
							val = val.substring(1, val.length() - 1);
						}
						if (System.getProperty(k.toString()) == null && System.getenv(k.toString()) == null) {
							System.setProperty(k.toString(), val);
						}
					});
					System.out.println("Loaded .env from: " + file.getAbsolutePath());
					break;
				} catch (Exception ignored) {
				}
			}
		}
	}
}
