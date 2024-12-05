package com.example.whattheeat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication

public class WhattheeatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhattheeatApplication.class, args);
    }

}
