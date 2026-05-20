package com.example.confighub.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class ConfigEvent {

    private final String eventType;
    private final String configKey;
    private final String configValue;
    private final Instant timestamp;

    @JsonCreator
    public ConfigEvent(
            @JsonProperty("eventType") String eventType,
            @JsonProperty("configKey") String configKey,
            @JsonProperty("configValue") String configValue,
            @JsonProperty("timestamp") Instant timestamp) {
        this.eventType = eventType;
        this.configKey = configKey;
        this.configValue = configValue;
        this.timestamp = timestamp;
    }

    public String getEventType() {
        return eventType;
    }

    public String getConfigKey() {
        return configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
