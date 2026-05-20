package com.example.confighub.controller;

import com.example.confighub.service.ConfigService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// Pattern 11: @SpringBootTest without @AutoConfigureMockMvc (must add @AutoConfigureMockMvc in 4.0)
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Pattern 11: In 4.0, @AutoConfigureMockMvc must be added explicitly
@SpringBootTest
@AutoConfigureMockMvc
class ConfigControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConfigService configService;

    @Test
    @WithMockUser
    void contextLoadsWithMockMvc() throws Exception {
        when(configService.getAllActiveConfigs()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/configs"))
                .andExpect(status().isOk());
    }
}
