package com.ai.spring_ai_test.config;

import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.anthropic.api.AnthropicApi;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.model.tool.DefaultToolCallingManager;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.tool.execution.DefaultToolExecutionExceptionProcessor;
import org.springframework.ai.tool.execution.ToolExecutionExceptionProcessor;
import org.springframework.ai.tool.resolution.ToolCallbackResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@RequiredArgsConstructor
public class DemoConfig {

    private final OpenAiProperties openAiProperties;
    private final ClaudeAiProperties claudeAiProperties;

    @Bean
    public OpenAiApi openAiApi() {
        return OpenAiApi.builder()
                .apiKey(openAiProperties.getOpenaiApiKey())
                .build();
    }

    @Bean
    AnthropicApi claudeApi() {
        return AnthropicApi.builder()
                .apiKey(claudeAiProperties.getApiKey())
                .build();
    }

    @Bean
    public MessageChatMemoryAdvisor messageChatMemoryAdvisor(final ChatMemory chatMemory) {
        return MessageChatMemoryAdvisor.builder(chatMemory).build();
    }

    @Bean
    public ChatClient claudeChatClient(
            final ToolCallingManager toolCallingManager,
            final RetryTemplate retryTemplate,
            final ObservationRegistry observationRegistry,
            final AnthropicApi claudeApi,
            final MessageChatMemoryAdvisor messageChatMemoryAdvisor
            ) {

        final AnthropicChatOptions chatOptions = new AnthropicChatOptions.Builder()
                .model(claudeAiProperties.getModels().get("claude-haiku"))
                .maxTokens(1024)
                .build();

        final AnthropicChatModel chatModel = new AnthropicChatModel(
                claudeApi,
                chatOptions,
                toolCallingManager,
                retryTemplate,
                observationRegistry
        );
        return ChatClient.builder(chatModel)
                .defaultAdvisors(messageChatMemoryAdvisor)
                .build();
    }

    @Bean
    public ChatClient gpt4miniChatClient(
            final ToolCallingManager toolCallingManager,
            final RetryTemplate retryTemplate,
            final ObservationRegistry observationRegistry,
            final OpenAiApi openAiApi) {

        final OpenAiChatOptions chatOptions = new OpenAiChatOptions.Builder()
                .model(openAiProperties.getModels().get("mini"))
                .build();
        final OpenAiChatModel chatModel = new OpenAiChatModel(
                openAiApi,
                chatOptions,
                toolCallingManager,
                retryTemplate,
                observationRegistry
        );

        return ChatClient.create(chatModel);
    }

    @Bean
    public ChatClient gpt5ChatClient(
            final ToolCallingManager toolCallingManager,
            final RetryTemplate retryTemplate,
            final ObservationRegistry observationRegistry,
            final OpenAiApi openAiApi) {

        final OpenAiChatOptions chatOptions = new OpenAiChatOptions.Builder()
                .model(openAiProperties.getModels().get("gpt5"))
                .build();
        final OpenAiChatModel chatModel = new OpenAiChatModel(
                openAiApi,
                chatOptions,
                toolCallingManager,
                retryTemplate,
                observationRegistry
        );

        return ChatClient.create(chatModel);
    }


    @Bean
    public ToolCallbackResolver toolCallbackResolver() {
        return toolName -> null;
    }

    @Bean
    public DefaultToolExecutionExceptionProcessor toolExecutionExceptionProcessor() {
        return new DefaultToolExecutionExceptionProcessor(true);
    }

    @Bean
    public ToolCallingManager toolCallingManager(
            final ObservationRegistry observationRegistry,
            final ToolCallbackResolver toolCallbackResolver,
            final ToolExecutionExceptionProcessor toolExecutionExceptionProcessor) {
        return new DefaultToolCallingManager(
                observationRegistry,
                toolCallbackResolver,
                toolExecutionExceptionProcessor
        );
    }

    @Bean
    public RetryTemplate retryTemplate() {
        return RetryTemplate.builder()
                .maxAttempts(3)
                .fixedBackoff(1000)
                .build();
    }

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(10)
                .build();
    }

}
