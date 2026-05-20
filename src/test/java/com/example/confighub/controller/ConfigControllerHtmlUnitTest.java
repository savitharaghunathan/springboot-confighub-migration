package com.example.confighub.controller;

import com.example.confighub.service.ConfigService;
import com.example.confighub.service.ConfigValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// Pattern 14: webClientEnabled=false (becomes @HtmlUnit(webClient=false) in 4.0)
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConfigController.class)
@AutoConfigureMockMvc(webClientEnabled = false)
class ConfigControllerHtmlUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConfigService configService;

    @MockBean
    private ConfigValidationService validationService;

    @Test
    @WithMockUser
    void getConfigs_withoutHtmlUnit() throws Exception {
        when(configService.getAllActiveConfigs()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/configs"))
                .andExpect(status().isOk());
    }
}
