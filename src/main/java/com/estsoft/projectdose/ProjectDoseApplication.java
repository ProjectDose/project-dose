package com.estsoft.projectdose;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjectDoseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectDoseApplication.class, args);
	}

}
