package com.own.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.own.*")
public class OwnTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(OwnTestApplication.class, args);
	}

}
