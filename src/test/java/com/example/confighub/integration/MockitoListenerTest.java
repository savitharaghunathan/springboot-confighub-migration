package com.example.confighub.integration;

import com.example.confighub.service.ConfigValidationService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
// Pattern 13: MockitoTestExecutionListener (removed in 4.0, use MockitoExtension instead)
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        MockitoTestExecutionListener.class
})
class MockitoListenerTest {

    // Pattern 15: @Mock/@Captor fields via MockitoTestExecutionListener (use MockitoExtension in 4.0)
    @Mock
    private ConfigValidationService configValidationService;

    @Captor
    private ArgumentCaptor<String> keyCaptor;

    @Test
    void mockInjectedViaListener() {
        assertNotNull(configValidationService);
        assertNotNull(keyCaptor);
    }
}
