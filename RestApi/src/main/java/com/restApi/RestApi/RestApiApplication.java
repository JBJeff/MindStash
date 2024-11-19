package com.restApi.RestApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
//spr@ComponentScan(basePackages = "com.restApi.RestApi.Basics") // Gibt das zu scannende Paket an
public class RestApiApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}

}




