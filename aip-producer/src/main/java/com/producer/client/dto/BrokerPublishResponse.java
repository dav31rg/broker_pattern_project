package com.producer.client.dto;

import java.time.Instant;

public record BrokerPublishResponse(
        String messageId,
        String topicName,
        int deliveredToSubscriptions,
        Instant publishedAt) {
}
