package com.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.producer.client.BrokerClient;

@SpringBootApplication
public class AipProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AipProducerApplication.class, args);
    }

    @Bean
    ApplicationRunner ensureOrdersTopicExists(BrokerClient brokerClient, @Value("${order.topic}") String topicName) {
        return args -> brokerClient.ensureTopicExists(topicName);
    }
}
