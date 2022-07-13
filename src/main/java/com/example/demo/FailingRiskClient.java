package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class FailingRiskClient {

    String evaluate() {
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (new Random().nextBoolean()) {
            throw new IllegalStateException();
        }
        if (new Random().nextBoolean()) {
            throw new IllegalStateException();
        }

        return "MANUAL";
    }
}
