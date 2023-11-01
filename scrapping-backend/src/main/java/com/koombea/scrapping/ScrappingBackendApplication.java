package com.koombea.scrapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.koombea.scrapping")
public class ScrappingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrappingBackendApplication.class, args);
	}

}
