package com.example.demo;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Endpoint(id = "random")
public class MyCustomEndpoint {

    @ReadOperation
    public String randomNumber() {
        return String.valueOf(new Random().nextInt());
    }
}
