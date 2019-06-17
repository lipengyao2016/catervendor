package com.vendor.testserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TestServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestServerApplication.class, args);
	}
}
