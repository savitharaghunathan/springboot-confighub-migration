package com.example.confighub.jackson;

// Pattern 1: com.fasterxml.jackson imports (become tools.jackson in 4.0)
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
// Pattern 2: @JsonComponent (becomes @JacksonComponent in 4.0)
import org.springframework.boot.jackson.JsonComponent;
// Pattern 4: JsonObjectSerializer (becomes ObjectValueSerializer in 4.0)
import org.springframework.boot.jackson.JsonObjectSerializer;

import java.io.IOException;
import java.time.Instant;

@JsonComponent
public class InstantSerializer extends JsonObjectSerializer<Instant> {

    @Override
    protected void serializeObject(Instant value, JsonGenerator jgen,
                                    SerializerProvider provider) throws IOException {
        jgen.writeNumberField("epochSecond", value.getEpochSecond());
        jgen.writeNumberField("nano", value.getNano());
        jgen.writeStringField("formatted", value.toString());
    }
}
