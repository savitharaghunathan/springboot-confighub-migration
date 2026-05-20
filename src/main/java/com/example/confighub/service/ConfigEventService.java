package com.example.confighub.service;

import com.example.confighub.model.ConfigEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Profile("kafka")
public class ConfigEventService {

    public void publishConfigChange(String key, String value, String eventType) {
        ConfigEvent event = new ConfigEvent(eventType, key, value, Instant.now());
        // Kafka publishing logic
    }
}
