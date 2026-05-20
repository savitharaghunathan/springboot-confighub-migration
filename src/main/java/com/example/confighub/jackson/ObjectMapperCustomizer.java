package com.example.confighub.jackson;

// Pattern 1: com.fasterxml.jackson imports (become tools.jackson in 4.0)
import com.fasterxml.jackson.databind.SerializationFeature;
// Pattern 6: Jackson2ObjectMapperBuilderCustomizer (becomes JsonMapperBuilderCustomizer in 4.0)
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperCustomizer implements Jackson2ObjectMapperBuilderCustomizer {

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        builder.failOnUnknownProperties(false);
    }
}
