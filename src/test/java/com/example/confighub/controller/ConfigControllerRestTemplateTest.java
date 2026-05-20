package com.example.confighub.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// Pattern 12: @SpringBootTest without @AutoConfigureTestRestTemplate (must add in 4.0)
import org.springframework.boot.test.context.SpringBootTest;
// Pattern 20: TestRestTemplate from old package (moves to o.s.b.resttestclient in 4.0)
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConfigControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getConfigs_viaRestTemplate() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/configs", String.class);
        assertNotNull(response);
    }
}
