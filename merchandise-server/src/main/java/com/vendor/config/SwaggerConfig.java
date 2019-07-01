package com.vendor.config;

import com.google.common.base.Predicate;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@SuppressWarnings({"deprecation","unchecked"})
@Configuration
@EnableSwagger2
//@Profile("pro")
public class SwaggerConfig {
	Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
		@Override
		public boolean apply(RequestHandler input) {
			Class<?> declaringClass = input.declaringClass();
			if (declaringClass == BasicErrorController.class)// 排除
				return false;
            return declaringClass.isAnnotationPresent(RestController.class);
        }
	};

	@Bean
	public Docket basicApi() {
		return new Docket(DocumentationType.SWAGGER_2).genericModelSubstitutes(DeferredResult.class)
				.useDefaultResponseMessages(false).forCodeGeneration(true)
				.pathMapping("/").select().apis(predicate)
				.paths(or(regex("/.*"))).build()
				.apiInfo(apiInfo("商品管理服务API"));
	}

	private ApiInfo apiInfo(String title) {
		return new ApiInfoBuilder().title(title).termsOfServiceUrl("/")
				.contact(new Contact("约范科技", "", ""))
				.version("1.0").build();
	}


}
