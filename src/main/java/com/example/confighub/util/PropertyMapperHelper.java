package com.example.confighub.util;

// Pattern 31: PropertyMapper.alwaysApplyingWhenNonNull() — removed in 4.0
import org.springframework.boot.context.properties.PropertyMapper;

import java.util.function.Consumer;

public class PropertyMapperHelper {

    public static void applyProperties(String name, String description,
                                        Consumer<String> nameConsumer,
                                        Consumer<String> descConsumer) {
        PropertyMapper mapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        mapper.from(name).to(nameConsumer);
        mapper.from(description).to(descConsumer);
    }
}
