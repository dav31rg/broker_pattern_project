package com.inventory.dto;

import java.time.Instant;

public record ConsumerStatusResponse(
        String consumerId,
        String topic,
        long processedCount,
        Long lastOrderId,
        Instant lastProcessedAt) {
}
