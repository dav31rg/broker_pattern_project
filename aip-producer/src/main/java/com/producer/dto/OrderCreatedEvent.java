package com.producer.dto;

import java.time.Instant;

public record OrderCreatedEvent(
        String eventType,
        long orderId,
        String customerName,
        String product,
        int quantity,
        Instant createdAt) {

    public static OrderCreatedEvent of(long orderId, OrderRequest request) {
        return new OrderCreatedEvent(
                "OrderCreatedEvent",
                orderId,
                request.customerName(),
                request.product(),
                request.quantity(),
                Instant.now());
    }
}
