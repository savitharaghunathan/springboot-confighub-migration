package com.example.confighub.controller;

import com.example.confighub.service.ConfigService;
import com.example.confighub.service.ConfigValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// Pattern 9: @MockBean (becomes @MockitoBean in 4.0)
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConfigController.class)
class ConfigControllerMockBeanTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConfigService configService;

    @MockBean
    private ConfigValidationService validationService;

    @Test
    @WithMockUser
    void getConfigs_returnsOk() throws Exception {
        when(configService.getAllActiveConfigs()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/configs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
