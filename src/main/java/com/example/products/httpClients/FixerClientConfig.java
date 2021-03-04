package com.example.products.httpClients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;

public class FixerClientConfig {
	
	@Value("${fixer.api-key}")
	private String apiKey;
	
	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			requestTemplate.query("access_key", apiKey);
		};
	}
}
