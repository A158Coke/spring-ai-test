package com.ai.spring_ai_test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient gpt4miniChatClient;

    private final ChatClient gpt5ChatClient;

    private final ChatClient claudeChatClient;

    private final ChatMemory chatMemory;

    public String getGpt4MiniResponse(final String prompt) {
        return gpt4miniChatClient
                .prompt(prompt)
                .call()
                .content();
    }

    public String getGpt5AIResponse(final String prompt) {
        return gpt5ChatClient
                .prompt(prompt)
                .call()
                .content();
    }

    public String getClaudeResponse(final String prompt) {
        List<Message> history = chatMemory.get("default");

        if(!CollectionUtils.isEmpty( history)){
            System.out.println("历史消息数: " + history.size());
            System.out.println("历史消息: " + history);
        }

        return claudeChatClient
                .prompt(prompt)
                .call()
                .content();

    }
}
