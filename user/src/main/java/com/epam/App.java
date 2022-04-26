package com.epam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;

@SpringBootApplication
@EnableDiscoveryClient
public class App {
	public static void main(String[] args) {

		SpringApplication.run(App.class, args);

	}

	@Bean
	public ModelMapper getMapper() {
		return new ModelMapper();
	}
}
