package com.notification.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "consumer")
public record ConsumerProperties(String id, String name, String topic, int pollMax) {
}
