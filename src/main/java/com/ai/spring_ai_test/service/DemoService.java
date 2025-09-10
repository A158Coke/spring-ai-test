package com.ai.spring_ai_test.service;

import com.ai.spring_ai_test.model.AIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private final ChatClient chatClient;

    public DemoService(final ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public AIResponse getAIResponse(final String prompt) {
        return chatClient
                .prompt(prompt)
                .user(prompt)
                .call()
                .entity(AIResponse.class);
    }
}
