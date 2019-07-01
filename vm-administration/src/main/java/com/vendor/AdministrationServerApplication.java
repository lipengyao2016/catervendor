package com.vendor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@MapperScan("com.vendor.mapper")
@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients
@EnableSwagger2
@ComponentScan(basePackages = {"com.vendor.beanconfig","com.vendor.interceptor"
		,"com.vendor.feign.administration","com.vendor.controller","com.vendor.service"
,"com.vendor.config","com.vendor.util","com.vendor.utils"})
public class AdministrationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdministrationServerApplication.class, args);
	}

}
