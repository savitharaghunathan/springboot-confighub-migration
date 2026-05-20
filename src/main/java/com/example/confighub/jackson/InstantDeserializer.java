package com.example.confighub.jackson;

// Pattern 1: com.fasterxml.jackson imports (become tools.jackson in 4.0)
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
// Pattern 2: @JsonComponent (becomes @JacksonComponent in 4.0)
import org.springframework.boot.jackson.JsonComponent;
// Pattern 5: JsonObjectDeserializer (becomes ObjectValueDeserializer in 4.0)
import org.springframework.boot.jackson.JsonObjectDeserializer;

import java.io.IOException;
import java.time.Instant;

@JsonComponent
public class InstantDeserializer extends JsonObjectDeserializer<Instant> {

    @Override
    protected Instant deserializeObject(JsonParser jsonParser,
                                         DeserializationContext context,
                                         ObjectCodec codec,
                                         JsonNode tree) throws IOException {
        long epochSecond = tree.get("epochSecond").asLong();
        int nano = tree.has("nano") ? tree.get("nano").asInt() : 0;
        return Instant.ofEpochSecond(epochSecond, nano);
    }
}
