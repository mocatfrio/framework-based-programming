package com.mocatfrio.restapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.mocatfrio")
@SpringBootApplication
public class BookManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookManagementApplication.class, args);
	}

}
