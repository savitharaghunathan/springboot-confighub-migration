package com.example.confighub.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    // Pattern 39: Undertow configuration (Undertow removed in 4.0)
    // This bean only activates when Undertow is on classpath (via the 'undertow' Maven profile)
    @Bean
    @ConditionalOnClass(name = "io.undertow.Undertow")
    public Object undertowCustomizer() {
        return new Object();
    }
}
