package com.dawid.overtime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class OvertimeApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Warsaw"));
	}
	public static void main(String[] args) {
		SpringApplication.run(OvertimeApplication.class, args);
	}

}
