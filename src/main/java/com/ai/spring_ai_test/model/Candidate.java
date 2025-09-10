package com.ai.spring_ai_test.model;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
public record Candidate(String target, String reason, double fitScore) {
}