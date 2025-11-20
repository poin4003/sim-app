package com.sim.app.sim_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.sim.app.sim_app.config.cors.CorsProperties;

@SpringBootApplication
@EnableConfigurationProperties(CorsProperties.class)
public class SimAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimAppApplication.class, args);
	}

}
