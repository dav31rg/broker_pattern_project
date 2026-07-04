package com.inventory.client.dto;

public record BrokerAckRequest(String consumerId, String deliveryId) {
}
