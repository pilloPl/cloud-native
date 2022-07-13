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
public class RiskController {

    private final FailingRiskClient riskClient;
    private final CircuitBreaker riskEvaluation;

    public RiskController(FailingRiskClient riskClient, CircuitBreakerFactory factory) {
        this.riskClient = riskClient;
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig
                .custom()
                .failureRateThreshold(5)
                .waitDurationInOpenState(Duration.ofMillis(300))
                .slidingWindowSize(2)
                .build();

        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(300))
                .build();

        factory.configureDefault(id -> new Resilience4JConfigBuilder("config").circuitBreakerConfig(circuitBreakerConfig)
                .timeLimiterConfig(timeLimiterConfig).build());

        this.riskEvaluation = factory.create("riskEvaluation");
    }

    @GetMapping("/evaluate")
    String evaluate() {
        return riskEvaluation.run(riskClient::evaluate, throwable -> "FALLBACK");
    }
}
