package com.samuel.memems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MemeMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemeMsApplication.class, args);
	}

}
