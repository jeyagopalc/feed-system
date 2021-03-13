package com.example.feedService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.example.feedService", "com.example.shared"})
@EnableMongoRepositories(basePackages = {"com.example.shared.repository"})
@EnableElasticsearchRepositories(basePackages = {"com.example.shared.repository.es"})
@EnableAutoConfiguration
public class FeedServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(FeedServiceApplication.class, args);
	}

}
