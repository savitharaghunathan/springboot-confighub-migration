package com.example.confighub.service;

import com.example.confighub.model.ConfigEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfigServiceTest {

    @Mock
    private ConfigRepository configRepository;

    @Captor
    private ArgumentCaptor<ConfigEntry> entryCaptor;

    @InjectMocks
    private ConfigService configService;

    @Test
    void getAllActiveConfigs_returnsActiveEntries() {
        ConfigEntry entry = new ConfigEntry("app.name", "Config Hub", "Application name");
        when(configRepository.findByActiveTrue()).thenReturn(List.of(entry));

        List<ConfigEntry> result = configService.getAllActiveConfigs();

        assertEquals(1, result.size());
        assertEquals("app.name", result.get(0).getKey());
    }

    @Test
    void getAllActiveConfigs_returnsEmptyWhenNone() {
        when(configRepository.findByActiveTrue()).thenReturn(Collections.emptyList());

        List<ConfigEntry> result = configService.getAllActiveConfigs();

        assertTrue(result.isEmpty());
    }

    @Test
    void getConfig_returnsEntryWhenFound() {
        ConfigEntry entry = new ConfigEntry("app.name", "Config Hub", "Application name");
        when(configRepository.findByKey("app.name")).thenReturn(Optional.of(entry));

        Optional<ConfigEntry> result = configService.getConfig("APP.NAME");

        assertTrue(result.isPresent());
        assertEquals("Config Hub", result.get().getValue());
    }

    @Test
    void getConfig_returnsEmptyWhenNotFound() {
        when(configRepository.findByKey("missing")).thenReturn(Optional.empty());

        Optional<ConfigEntry> result = configService.getConfig("missing");

        assertTrue(result.isEmpty());
    }

    @Test
    void createConfig_savesEntry() {
        when(configRepository.save(any(ConfigEntry.class))).thenAnswer(invocation -> {
            ConfigEntry saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        ConfigEntry result = configService.createConfig("app.name", "Config Hub", "Application name");

        verify(configRepository).save(entryCaptor.capture());
        ConfigEntry captured = entryCaptor.getValue();
        assertEquals("app.name", captured.getKey());
        assertEquals("Config Hub", captured.getValue());
        assertEquals("Application name", captured.getDescription());
    }

    @Test
    void createConfig_throwsOnEmptyKey() {
        assertThrows(IllegalArgumentException.class,
                () -> configService.createConfig("", "value", "desc"));
    }

    @Test
    void updateConfig_updatesExistingEntry() {
        ConfigEntry existing = new ConfigEntry("app.name", "Old Value", "desc");
        when(configRepository.findByKey("app.name")).thenReturn(Optional.of(existing));
        when(configRepository.save(any(ConfigEntry.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<ConfigEntry> result = configService.updateConfig("app.name", "New Value");

        assertTrue(result.isPresent());
        assertEquals("New Value", result.get().getValue());
    }

    @Test
    void deleteConfig_softDeletesEntry() {
        ConfigEntry existing = new ConfigEntry("app.name", "value", "desc");
        existing.setActive(true);
        when(configRepository.findByKey("app.name")).thenReturn(Optional.of(existing));
        when(configRepository.save(any(ConfigEntry.class))).thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = configService.deleteConfig("app.name");

        assertTrue(result);
        verify(configRepository).save(entryCaptor.capture());
        assertFalse(entryCaptor.getValue().isActive());
    }

    @Test
    void deleteConfig_returnsFalseWhenNotFound() {
        when(configRepository.findByKey("missing")).thenReturn(Optional.empty());

        boolean result = configService.deleteConfig("missing");

        assertFalse(result);
    }
}
