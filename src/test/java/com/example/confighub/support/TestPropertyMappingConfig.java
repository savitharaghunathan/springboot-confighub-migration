package com.example.confighub.support;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// Pattern 19: PropertyMapping from old test package (moves to o.s.b.test.context in 4.0)
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;

@Retention(RetentionPolicy.RUNTIME)
@PropertyMapping("confighub.test")
public @interface TestPropertyMappingConfig {
    String value() default "default";
}
