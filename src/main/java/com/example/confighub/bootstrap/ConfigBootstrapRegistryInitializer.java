package com.example.confighub.bootstrap;

// Pattern 16: BootstrapRegistry from old package (moves to o.s.b.bootstrap in 4.0)
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.BootstrapRegistryInitializer;

public class ConfigBootstrapRegistryInitializer implements BootstrapRegistryInitializer {

    @Override
    public void initialize(BootstrapRegistry registry) {
        registry.register(String.class, context -> "confighub-bootstrap-value");
    }
}
