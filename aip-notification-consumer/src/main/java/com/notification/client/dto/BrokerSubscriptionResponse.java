package com.notification.client.dto;

import java.time.Instant;

public record BrokerSubscriptionResponse(
        String subscriptionId,
        String topicName,
        String consumerId,
        Instant subscribedAt) {
}
