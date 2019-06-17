package com.vendor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients
@MapperScan("com.vendor.mapper")
@ComponentScan(basePackages = {"com.vendor.controller","com.vendor.service"
		,"com.vendor.config","com.vendor.utils",
		"com.vendor.interceptor","com.vendor.feign.administration"})
public class MerchantServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MerchantServerApplication.class, args);
	}
}
