package com.ai.spring_ai_test.controller;

import com.ai.spring_ai_test.model.AIResponse;
import com.ai.spring_ai_test.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
public class HelloController {

    private final DemoService demoService;

    @PostMapping
    public AIResponse hello(@Valid final String prompt) {
        return demoService.getAIResponse( prompt);
    }
}
