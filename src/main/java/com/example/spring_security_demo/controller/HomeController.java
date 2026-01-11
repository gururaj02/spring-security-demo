package com.example.spring_security_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String homePage() {
        return "Home Page - Learning Spring Security";
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Healthy";
    }

    @GetMapping("/greet")
    public String greet() {
        return "Hello All";
    }
}
