package com.example.confighub.service;

import com.example.confighub.model.ConfigEntry;
import com.example.confighub.util.NullableUtils;
import com.example.confighub.util.PropertyMapperHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigService {

    private final ConfigRepository configRepository;

    public ConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public List<ConfigEntry> getAllActiveConfigs() {
        return configRepository.findByActiveTrue();
    }

    public Optional<ConfigEntry> getConfig(String key) {
        String normalizedKey = NullableUtils.normalizeKey(key);
        return configRepository.findByKey(normalizedKey);
    }

    @Transactional
    public ConfigEntry createConfig(String key, String value, String description) {
        String normalizedKey = NullableUtils.requireNonEmpty(key, "key");
        NullableUtils.requireNonEmpty(value, "value");

        ConfigEntry entry = new ConfigEntry();
        PropertyMapperHelper.applyProperties(
                normalizedKey, description,
                entry::setKey, entry::setDescription
        );
        entry.setValue(value);
        entry.setCreatedAt(Instant.now());
        entry.setUpdatedAt(Instant.now());

        return configRepository.save(entry);
    }

    @Transactional
    public Optional<ConfigEntry> updateConfig(String key, String value) {
        return configRepository.findByKey(key).map(entry -> {
            entry.setValue(value);
            entry.setUpdatedAt(Instant.now());
            return configRepository.save(entry);
        });
    }

    @Transactional
    public boolean deleteConfig(String key) {
        return configRepository.findByKey(key).map(entry -> {
            entry.setActive(false);
            entry.setUpdatedAt(Instant.now());
            configRepository.save(entry);
            return true;
        }).orElse(false);
    }
}
