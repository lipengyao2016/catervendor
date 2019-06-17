package com.vendor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@EnableZuulProxy
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.vendor.filter"})
@SpringBootApplication
public class OperationApiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OperationApiServerApplication.class, args);
	}
}
