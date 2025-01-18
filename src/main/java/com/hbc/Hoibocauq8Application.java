package com.hbc;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Hoibocauq8Application {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+07:00"));
		SpringApplication.run(Hoibocauq8Application.class, args);
	}
}
