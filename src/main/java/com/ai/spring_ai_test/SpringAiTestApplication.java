package com.ai.spring_ai_test;

import com.ai.spring_ai_test.config.OpenAiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(OpenAiProperties.class)
public class SpringAiTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAiTestApplication.class, args);
	}

}
