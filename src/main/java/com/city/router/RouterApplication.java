package com.city.router;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.city")
public class RouterApplication {
	public static void main(String[] args) {
		SpringApplication.run(RouterApplication.class, args);
	}

}
