package com.notification.client.dto;

public record BrokerSubscribeRequest(String topicName, String consumerId, String consumerName) {
}
