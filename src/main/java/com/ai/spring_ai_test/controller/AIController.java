package com.ai.spring_ai_test.controller;

import com.ai.spring_ai_test.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AIController {

    private final AIService AIService;

    @PostMapping("/ai")
    public String getJoke(@RequestParam("prompt") final String prompt, @RequestParam("model") final String model) {
        return switch (model) {
            case "gpt5" -> AIService.getGpt5AIResponse(prompt);
            case "mini" -> AIService.getGpt4MiniResponse(prompt);
            case "claude" -> AIService.getClaudeResponse(prompt);
            default -> "no model found";
        };
    }
}
