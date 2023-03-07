package com.example.demo;

import com.example.demo.controlleradvice.MovieControllerAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(MovieControllerAdvice.class)
public class FujitsuTaskApplication {


	public static void main(String[] args) {
		SpringApplication.run(FujitsuTaskApplication.class, args);
	}

}