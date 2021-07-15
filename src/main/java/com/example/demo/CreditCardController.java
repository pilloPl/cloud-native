package com.example.demo;


import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
public class CreditCardController {

    final FailingRiskClient failingRiskClient;
    private final CircuitBreaker riskEvaluation;

    public CreditCardController(FailingRiskClient failingRiskClient, CircuitBreakerFactory circuitBreakerFactory) {
        this.failingRiskClient = failingRiskClient;

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig
                .custom()
                .failureRateThreshold(5)
                .waitDurationInOpenState(Duration.ofMillis(300))
                .slidingWindowSize(2)
                .build();

        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(300))
                .build();

        circuitBreakerFactory.configureDefault(id -> new Resilience4JConfigBuilder("config").circuitBreakerConfig(circuitBreakerConfig)
        .timeLimiterConfig(timeLimiterConfig).build());

        this.riskEvaluation = circuitBreakerFactory.create("riskEvaluation");

    }

    @GetMapping("/evaluate")
    String evaluate() {
        return riskEvaluation.run(() -> failingRiskClient.evaluate());
    }
}
