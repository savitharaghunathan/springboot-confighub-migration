package com.example.confighub.rabbit;

// Pattern 35: RabbitRetryTemplateCustomizer (split into two customizers in 4.0)
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;

@Configuration
@Profile("rabbit")
public class RabbitRetryConfig {

    @Bean
    public RabbitRetryTemplateCustomizer rabbitRetryCustomizer() {
        return (target, retryTemplate) -> {
            ExponentialBackOffPolicy backOff = new ExponentialBackOffPolicy();
            backOff.setInitialInterval(500);
            backOff.setMaxInterval(10000);
            backOff.setMultiplier(2.0);
            retryTemplate.setBackOffPolicy(backOff);
        };
    }
}
