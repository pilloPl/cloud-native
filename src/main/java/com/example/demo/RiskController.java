package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RiskController {

    private final FailingRiskClient riskClient;

    public RiskController(FailingRiskClient riskClient) {
        this.riskClient = riskClient;
    }

    @GetMapping("/evaluate")
    String evaluate() {
        return riskClient.evaluate();
    }
}
