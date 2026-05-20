package com.example.confighub.controller;

import com.example.confighub.model.AuditInfo;
import com.example.confighub.model.ConfigEntry;
import com.example.confighub.service.ConfigService;
import com.example.confighub.service.ConfigValidationService;
// Pattern 1: com.fasterxml.jackson imports
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/configs")
public class ConfigController {

    private final ConfigService configService;
    private final ConfigValidationService validationService;
    private final ObjectMapper objectMapper;

    public ConfigController(ConfigService configService,
                            ConfigValidationService validationService,
                            ObjectMapper objectMapper) {
        this.configService = configService;
        this.validationService = validationService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<List<ConfigEntry>> getAllConfigs() {
        return ResponseEntity.ok(configService.getAllActiveConfigs());
    }

    @GetMapping("/{key}")
    public ResponseEntity<ConfigEntry> getConfig(@PathVariable String key) {
        return configService.getConfig(key)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createConfig(@RequestBody Map<String, String> body) {
        String key = body.get("key");
        String value = body.get("value");
        String description = body.get("description");

        List<String> errors = validationService.validateConfig(key, value);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("errors", errors));
        }

        ConfigEntry entry = configService.createConfig(key, value, description);
        return ResponseEntity.status(HttpStatus.CREATED).body(entry);
    }

    @PutMapping("/{key}")
    public ResponseEntity<?> updateConfig(@PathVariable String key,
                                          @RequestBody Map<String, String> body) {
        String value = body.get("value");
        return configService.updateConfig(key, value)
                .map(entry -> ResponseEntity.ok((Object) entry))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> deleteConfig(@PathVariable String key) {
        if (configService.deleteConfig(key)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/audit")
    public ResponseEntity<AuditInfo> getAuditInfo() {
        AuditInfo audit = new AuditInfo("system", "system", Instant.now(), Instant.now());
        audit.setInternalTraceId("trace-" + System.currentTimeMillis());
        return ResponseEntity.ok(audit);
    }
}
