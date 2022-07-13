package com.example.demo;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Service;

import java.util.Random;

@Endpoint(id = "random")
@Service
public class MyCustomEndpoint {

    @ReadOperation
    Integer randomNumber() {
        return new Random().nextInt();
    }

}