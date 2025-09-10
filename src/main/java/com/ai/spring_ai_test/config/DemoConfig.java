package com.ai.spring_ai_test.config;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DemoConfig {
    private final OpenAiChatModel openAiChatModel;

    @Bean
    public ChatClient chatClient() {
        return ChatClient.create(openAiChatModel);
    }

    @Bean
    protected OpenAiChatModel openAiChatModel() {
        return OpenAiChatModel.builder().build();
    }
}
