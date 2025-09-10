package com.ai.spring_ai_test.model;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Builder
public record AIResponse(List<Candidate> candidates, String recommendation) {
}
