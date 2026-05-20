package com.example.confighub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("redis")
public class RedisConfig {
    // Redis auto-configuration via spring.data.redis.* and spring.session.redis.* properties (Pattern 26)
    // Properties in application-redis.properties
}
