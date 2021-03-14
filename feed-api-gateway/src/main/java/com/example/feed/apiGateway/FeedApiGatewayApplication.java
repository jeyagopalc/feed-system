
package com.example.feed.apiGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FeedApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedApiGatewayApplication.class, args);
	}
}
