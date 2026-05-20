package com.example.confighub.bootstrap;

// Pattern 17: EnvironmentPostProcessor from old package (moves to o.s.b.EnvironmentPostProcessor in 4.0)
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class ConfigEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment,
                                        SpringApplication application) {
        Map<String, Object> defaults = new HashMap<>();
        defaults.put("confighub.defaults.loaded", "true");
        defaults.put("confighub.version", "1.0.0");
        environment.getPropertySources().addLast(
                new MapPropertySource("confighub-defaults", defaults));
    }
}
