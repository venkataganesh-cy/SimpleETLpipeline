package com.etldemo.titanic_pipeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TitanicPipelineApplication {

	public static void main(String[] args) {
		SpringApplication.run(TitanicPipelineApplication.class, args);
	}

}
