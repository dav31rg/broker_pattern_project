package com.producer.client.dto;

public record BrokerPublishRequest(String topicName, String payload, String producerId) {
}
