package com.codewithrupaliblog.config;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwagggerConfig {
	
//ye swagger ki configurtion he yaha pe aap jo bhi karoge o sab dikhai dega docs me jab app
	//http://localhost:9191/swagger-ui/index.html ye url dete ho to
	
	public static final String AUTHORIZATION_HEADER = "Authorization";

	
//api key banate he	 isai api key ko jo
	private ApiKey apiKeys() 
	{
		           //   Key name    header
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}
	
	
//security contex ko banane ke liye method create kiya jo return kar raha he security Context
//securityContxt ke pass ek method he builder.builder me hum ek chiz pass karnge securityReferences
//ye hume return karega sucurity context use as list return karo
	private List<SecurityContext> securityContexts() 
	{
		
		return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
		
	}
	
	

//ye method SecurityReferencec pass return karegi sf yesi hi naam diya method ko
	private List<SecurityReference> sf() {
                                                       //scope           description
		AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");

//AuthorizationScope ek single veribale he aur hum arry pass kar rahe he to hum annoysm array 
//use karte he
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] { scope }));
	}
	
	
	
	
////doker Class is apiinfo ko  yaha pe use karegi with paths(PathSelectors)
	//Arrays.asList(apiKeys() api keys ko as a list pass karnge
	@Bean
	public Docket api() {

		//swagger2 use karega internally apiInfo me hum saari informtion denge ye information 
		//ayegi kaha se getInfo method se
		//RequestHandlerSelectors.any() matlab saari apis
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo()).securityContexts(securityContexts())
			.securitySchemes(Arrays.asList(apiKeys()))
			.select().apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any()).build();

	}

	//ye method api ki saari information return karat he

	private ApiInfo getInfo() {

//                 1     2          3        4                 5           6        7
//apiInfo me hume title,description,version,termsOfServiceUrl,ContactName,License,license Url 
//ye saari chize pass karni hoti he
//contact me naam, emailid,website name
//Collections.emptyList() ye ek vendorExtension he sirf
		
		return new ApiInfo("Blogging Application : Backend Course",
				"This project is developed by Learn Code With Durgesh",
				"1.0", 
				"Terms of Service",     //apani website ka naam dedo protfolio
				new Contact("Rupali", "https://learncodewithRupali.com", "RupaliBhore215@gmail.com"),
				"License of APIS", "API license URL", Collections.emptyList());   
	};

}
