package com.producer.service;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.producer.client.BrokerClient;
import com.producer.client.dto.BrokerPublishResponse;
import com.producer.dto.OrderCreatedEvent;
import com.producer.dto.OrderRequest;
import com.producer.dto.OrderResponse;
import tools.jackson.databind.ObjectMapper;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private static final String PRODUCER_ID = "producer-service";

    private final BrokerClient brokerClient;
    private final ObjectMapper objectMapper;
    private final String topicName;
    private final AtomicLong orderSequence = new AtomicLong(100);

    public OrderService(
            BrokerClient brokerClient,
            ObjectMapper objectMapper,
            @Value("${order.topic}") String topicName) {
        this.brokerClient = brokerClient;
        this.objectMapper = objectMapper;
        this.topicName = topicName;
    }

    public OrderResponse createOrder(OrderRequest request) {
        long orderId = orderSequence.getAndIncrement();
        OrderCreatedEvent event = OrderCreatedEvent.of(orderId, request);

        String payload = objectMapper.writeValueAsString(event);
        BrokerPublishResponse result = brokerClient.publish(topicName, payload, PRODUCER_ID);

        log.info("Pedido #{} creado y publicado en el topic '{}'.", orderId, topicName);

        return new OrderResponse(
                orderId,
                "PUBLISHED",
                result.messageId(),
                result.topicName(),
                result.deliveredToSubscriptions(),
                result.publishedAt());
    }
}
