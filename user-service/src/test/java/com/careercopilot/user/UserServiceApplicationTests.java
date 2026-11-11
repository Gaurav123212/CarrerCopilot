package com.careercopilot.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

@SpringBootTest
class UserServiceApplicationTests {

	@BeforeAll
	static void setup() {
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
					break;
				} catch (Exception ignored) {
				}
			}
		}
	}

	@Test
	void contextLoads() {
	}

}
