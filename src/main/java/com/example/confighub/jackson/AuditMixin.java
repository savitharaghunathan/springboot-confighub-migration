package com.example.confighub.jackson;

// Pattern 1: com.fasterxml.jackson imports (become tools.jackson in 4.0)
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// Pattern 3: @JsonMixin (becomes @JacksonMixin in 4.0)
import org.springframework.boot.jackson.JsonMixin;

import com.example.confighub.model.AuditInfo;

@JsonMixin(AuditInfo.class)
@JsonIgnoreProperties({"internalTraceId"})
public abstract class AuditMixin {
}
