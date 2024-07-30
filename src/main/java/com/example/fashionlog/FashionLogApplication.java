package com.example.fashionlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FashionLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(FashionLogApplication.class, args);
	}
}