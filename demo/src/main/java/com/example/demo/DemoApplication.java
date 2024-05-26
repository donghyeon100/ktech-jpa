package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Log4j2
@Slf4j
public class DemoApplication {
	
	public static void main(String[] args) {
		
		log.info("AAA");
		log.info("AAA");
		log.info("AAA");
		log.info("AAA");
		log.info("AAA");
		
		SpringApplication.run(DemoApplication.class, args);
	}

}
