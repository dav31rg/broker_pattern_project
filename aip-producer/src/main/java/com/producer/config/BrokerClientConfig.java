package com.producer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class BrokerClientConfig {

    @Bean
    public RestClient brokerRestClient(@Value("${broker.base-url}") String brokerBaseUrl) {
        return RestClient.builder()
                .baseUrl(brokerBaseUrl)
                .build();
    }
}
