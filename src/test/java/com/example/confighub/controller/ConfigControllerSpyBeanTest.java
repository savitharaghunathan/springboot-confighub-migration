package com.example.confighub.controller;

import com.example.confighub.service.ConfigService;
import com.example.confighub.service.ConfigValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
// Pattern 10: @SpyBean (becomes @MockitoSpyBean in 4.0)
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConfigController.class)
class ConfigControllerSpyBeanTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConfigService configService;

    @SpyBean
    private ConfigValidationService validationService;

    @Test
    @WithMockUser
    void getConfigs_delegatesToRealValidation() throws Exception {
        mockMvc.perform(get("/api/configs"))
                .andExpect(status().isOk());
    }
}
