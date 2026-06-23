package com.exam.recipeplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class RecipeplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeplatformApplication.class, args);
	}
}