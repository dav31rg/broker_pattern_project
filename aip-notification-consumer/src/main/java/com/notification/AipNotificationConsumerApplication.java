package com.notification;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.notification.client.BrokerClient;
import com.notification.config.ConsumerProperties;

@SpringBootApplication
public class AipNotificationConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AipNotificationConsumerApplication.class, args);
    }

    @Bean
    ApplicationRunner registerAndSubscribe(BrokerClient brokerClient, ConsumerProperties properties) {
        return args -> {
            brokerClient.ensureTopicExists(properties.topic());
            brokerClient.subscribe(properties.topic(), properties.id(), properties.name());
        };
    }
}
