package com.vendor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@MapperScan("com.vendor.mapper")
@ComponentScan(basePackages = {"com.vendor.controller","com.vendor.service",
		"com.vendor.config","com.vendor.utils"})
class RoleBaticsServerApplication {

	public static void main(String[] args) {

        System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(RoleBaticsServerApplication.class, args);
	}

}
