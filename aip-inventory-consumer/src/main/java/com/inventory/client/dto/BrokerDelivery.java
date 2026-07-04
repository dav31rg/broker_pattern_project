package com.inventory.client.dto;

import java.time.Instant;

public record BrokerDelivery(
        String deliveryId,
        String messageId,
        String topicName,
        String payload,
        String status,
        Instant publishedAt,
        Instant deliveredAt,
        Instant ackedAt) {
}
