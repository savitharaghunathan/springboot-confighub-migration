package com.example.confighub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
// Pattern 34: StreamsBuilderFactoryBeanCustomizer (becomes StreamsBuilderFactoryBeanConfigurer in 4.0)
import org.springframework.boot.autoconfigure.kafka.StreamsBuilderFactoryBeanCustomizer;

@Configuration
@Profile("kafka")
public class KafkaConfig {

    @Bean
    public StreamsBuilderFactoryBeanCustomizer streamsCustomizer() {
        return factoryBean -> {
            factoryBean.setAutoStartup(false);
        };
    }
}
