package com.example.confighub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("mongodb")
public class MongoConfig {
    // MongoDB auto-configuration via spring.data.mongodb.* properties (Pattern 25)
    // Properties in application-mongodb.properties
}
