package com.yns.forex.simpleforexapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SimpleForexApiApplication {

	@Value("${api.version}")         String apiVersion;
	@Value("${api.title}")           String apiTitle;
	@Value("${api.description}")     String apiDescription;
	@Value("${api.license}")         String apiLicense;
	@Value("${api.contact.name}")    String apiContactName;
	@Value("${api.contact.url}")     String apiContactUrl;
	@Value("${api.contact.email}")   String apiContactEmail;

	public static void main(String[] args) {
		SpringApplication.run(SimpleForexApiApplication.class, args);
	}


	/**
	 * Will exposed on $HOST:$PORT/swagger-ui.html
	 */
	@Bean
	public OpenAPI getOpenApiDocumentation() {
		return new OpenAPI().info(new Info().title(apiTitle)
						.description(apiDescription)
						.version(apiVersion)
						.contact(new Contact()
								.name(apiContactName)
								.url(apiContactUrl)
								.email(apiContactEmail))
						.license(new License()
								.name(apiLicense)));
	}

}
