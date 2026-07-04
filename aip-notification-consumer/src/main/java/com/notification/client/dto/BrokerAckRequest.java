package com.notification.client.dto;

public record BrokerAckRequest(String consumerId, String deliveryId) {
}
