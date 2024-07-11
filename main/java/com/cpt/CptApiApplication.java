package com.cpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
//@SpringBootApplication(scanBasePackages = {"com.cpt.service"})
public class CptApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CptApiApplication.class, args);
	}

}
