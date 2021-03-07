package com.example.postService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"com.example.shared.dal"})
public class PostServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(PostServiceApplication.class, args);
	}

}
