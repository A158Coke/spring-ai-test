package com.ai.spring_ai_test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "spring.ai.openai")
public class OpenAiProperties {
    private String openaiApiKey;
    private Map<String, String> models;
}
