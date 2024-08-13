package com.muse.lovely;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LovelyApplication {

	public static void main(String[] args) {
		SpringApplication.run(LovelyApplication.class, args);
	}

}
