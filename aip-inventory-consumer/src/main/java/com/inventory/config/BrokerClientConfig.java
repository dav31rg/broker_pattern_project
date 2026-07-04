package com.inventory.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestClient;

@Configuration
@EnableScheduling
@EnableConfigurationProperties(ConsumerProperties.class)
public class BrokerClientConfig {

    @Bean
    public RestClient brokerRestClient(@Value("${broker.base-url}") String brokerBaseUrl) {
        return RestClient.builder()
                .baseUrl(brokerBaseUrl)
                .build();
    }
}
