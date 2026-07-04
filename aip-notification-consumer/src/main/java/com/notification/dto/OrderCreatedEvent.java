package com.notification.dto;

import java.time.Instant;

public record OrderCreatedEvent(
        String eventType,
        long orderId,
        String customerName,
        String product,
        int quantity,
        Instant createdAt) {
}
