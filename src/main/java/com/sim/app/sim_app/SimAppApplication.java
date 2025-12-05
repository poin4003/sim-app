package com.sim.app.sim_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimAppApplication.class, args);
	}

}
