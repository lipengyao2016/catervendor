package com.vendor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.util.UrlPathHelper;

@SpringBootApplication
//@MapperScan("com.vendor.mapper")
@ComponentScan(basePackages = {"com.vendor.controller","com.vendor.service","com.vendor.config"
,"com.vendor.utils"})
public class RoleBaticsPlusServerApplication /*extends WebMvcConfigurerAdapter */{

	public static void main(String[] args) {
		SpringApplication.run(RoleBaticsPlusServerApplication.class, args);
	}

	/*@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		urlPathHelper.setUrlDecode(false);
		configurer.setUrlPathHelper(urlPathHelper);
	}*/

}
