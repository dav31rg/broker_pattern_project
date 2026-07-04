package com.producer.dto;

import java.time.Instant;

public record OrderResponse(
        long orderId,
        String status,
        String messageId,
        String topicName,
        int deliveredToSubscriptions,
        Instant publishedAt) {
}
