package com.neelkanth.headerbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HeaderBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HeaderBackendApplication.class, args);
	}

}
