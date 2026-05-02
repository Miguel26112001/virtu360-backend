package com.example.virtu360;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Virtu360Application {

	public static void main(String[] args) {
		SpringApplication.run(Virtu360Application.class, args);
	}

}
