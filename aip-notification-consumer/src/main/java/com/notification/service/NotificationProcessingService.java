package com.notification.service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.notification.client.BrokerClient;
import com.notification.client.dto.BrokerDelivery;
import com.notification.config.ConsumerProperties;
import com.notification.dto.ConsumerStatusResponse;
import com.notification.dto.OrderCreatedEvent;

import tools.jackson.databind.ObjectMapper;

@Service
public class NotificationProcessingService {

    private static final Logger log = LoggerFactory.getLogger(NotificationProcessingService.class);

    private final BrokerClient brokerClient;
    private final ObjectMapper objectMapper;
    private final ConsumerProperties properties;

    private final AtomicLong processedCount = new AtomicLong(0);
    private final AtomicReference<Long> lastOrderId = new AtomicReference<>();
    private final AtomicReference<Instant> lastProcessedAt = new AtomicReference<>();

    public NotificationProcessingService(
            BrokerClient brokerClient, ObjectMapper objectMapper, ConsumerProperties properties) {
        this.brokerClient = brokerClient;
        this.objectMapper = objectMapper;
        this.properties = properties;
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    public void pollAndProcess() {
        List<BrokerDelivery> deliveries = brokerClient.consume(properties.id(), properties.pollMax());
        deliveries.forEach(this::process);
    }

    private void process(BrokerDelivery delivery) {
        OrderCreatedEvent event = objectMapper.readValue(delivery.payload(), OrderCreatedEvent.class);

        log.info("Pedido #{} recibido.", event.orderId());
        log.info("Enviando correo al cliente...");
        log.info("Correo enviado.");

        brokerClient.ack(properties.id(), delivery.deliveryId());
        log.info("ACK enviado.");

        processedCount.incrementAndGet();
        lastOrderId.set(event.orderId());
        lastProcessedAt.set(Instant.now());
    }

    public ConsumerStatusResponse status() {
        return new ConsumerStatusResponse(
                properties.id(), properties.topic(), processedCount.get(), lastOrderId.get(), lastProcessedAt.get());
    }
}
