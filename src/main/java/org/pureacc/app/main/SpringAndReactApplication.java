package org.pureacc.app.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.pureacc.app")
public class SpringAndReactApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringAndReactApplication.class, args);
	}
}
