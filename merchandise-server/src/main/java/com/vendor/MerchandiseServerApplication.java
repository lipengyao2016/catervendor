package com.vendor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableDiscoveryClient
@EnableHystrix
@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"com.vendor.controller","com.vendor.service","com.vendor.config"
		,"com.vendor.utils"})
public class MerchandiseServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MerchandiseServerApplication.class, args);
	}
}
