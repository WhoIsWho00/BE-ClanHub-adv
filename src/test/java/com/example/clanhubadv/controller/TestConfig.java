package com.example.clanhubadv.controller;



import com.example.clanhubadv.Security.JWT.JwtCore;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public JwtCore jwtCore() {
        return new JwtCore();
    }
}