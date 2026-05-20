package com.example.confighub.config;

import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClientBuilder;
// Pattern 36: RestClientBuilderCustomizer (becomes Rest5ClientBuilderCustomizer in 4.0)
import org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("elasticsearch")
public class ElasticsearchConfig implements RestClientBuilderCustomizer {

    @Override
    public void customize(RestClientBuilder builder) {
        builder.setRequestConfigCallback(requestConfigBuilder ->
                requestConfigBuilder
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
        );
    }

    @Override
    public void customize(HttpAsyncClientBuilder builder) {
        builder.setMaxConnTotal(100);
    }
}
