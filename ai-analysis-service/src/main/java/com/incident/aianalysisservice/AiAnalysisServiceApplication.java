package com.incident.aianalysisservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AiAnalysisServiceApplication {
	@Bean
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(5000);
		factory.setReadTimeout(60000); // 🔥 60 seconds
		return new RestTemplate(factory);
	}
	public static void main(String[] args) {
		SpringApplication.run(AiAnalysisServiceApplication.class, args);
	}

}
