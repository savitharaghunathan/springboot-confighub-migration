package com.example.confighub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// Pattern 18: @EntityScan from old location (moves to o.s.b.persistence.autoconfigure in 4.0)
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = "com.example.confighub.model")
public class ConfigHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigHubApplication.class, args);
    }
}
