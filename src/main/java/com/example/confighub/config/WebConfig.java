package com.example.confighub.config;

// Pattern 32: HttpMessageConverters bean (deprecated in 4.0, use ServerHttpMessageConvertersCustomizer)
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class WebConfig {

    @Bean
    public HttpMessageConverters customConverters() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        return new HttpMessageConverters(jsonConverter);
    }
}
