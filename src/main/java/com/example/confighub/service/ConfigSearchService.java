package com.example.confighub.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Profile("elasticsearch")
public class ConfigSearchService {

    public List<String> searchConfigs(String query) {
        return Collections.emptyList();
    }
}
