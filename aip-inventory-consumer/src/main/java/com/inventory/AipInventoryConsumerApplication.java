package com.inventory;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.inventory.client.BrokerClient;
import com.inventory.config.ConsumerProperties;

@SpringBootApplication
public class AipInventoryConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AipInventoryConsumerApplication.class, args);
    }

    @Bean
    ApplicationRunner registerAndSubscribe(BrokerClient brokerClient, ConsumerProperties properties) {
        return args -> {
            brokerClient.ensureTopicExists(properties.topic());
            brokerClient.subscribe(properties.topic(), properties.id(), properties.name());
        };
    }
}
