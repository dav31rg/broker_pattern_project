package com.notification.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.notification.client.dto.BrokerAckRequest;
import com.notification.client.dto.BrokerDelivery;
import com.notification.client.dto.BrokerRegisterTopicRequest;
import com.notification.client.dto.BrokerSubscribeRequest;
import com.notification.client.dto.BrokerSubscriptionResponse;

@Component
public class BrokerClient {

    private static final Logger log = LoggerFactory.getLogger(BrokerClient.class);

    private final RestClient restClient;

    public BrokerClient(RestClient brokerRestClient) {
        this.restClient = brokerRestClient;
    }

    public void ensureTopicExists(String topicName) {
        try {
            restClient.post()
                    .uri("/topics/register")
                    .body(new BrokerRegisterTopicRequest(topicName))
                    .retrieve()
                    .toBodilessEntity();
            log.info("Topic '{}' registrado en el Broker.", topicName);
        } catch (HttpClientErrorException.Conflict ex) {
            log.info("Topic '{}' ya existia en el Broker.", topicName);
        }
    }

    public BrokerSubscriptionResponse subscribe(String topicName, String consumerId, String consumerName) {
        return restClient.post()
                .uri("/subscribe")
                .body(new BrokerSubscribeRequest(topicName, consumerId, consumerName))
                .retrieve()
                .body(BrokerSubscriptionResponse.class);
    }

    public List<BrokerDelivery> consume(String consumerId, int max) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/consume")
                        .queryParam("consumerId", consumerId)
                        .queryParam("max", max)
                        .build())
                .retrieve()
                .body(new org.springframework.core.ParameterizedTypeReference<List<BrokerDelivery>>() {
                });
    }

    public void ack(String consumerId, String deliveryId) {
        restClient.post()
                .uri("/ack")
                .body(new BrokerAckRequest(consumerId, deliveryId))
                .retrieve()
                .toBodilessEntity();
    }
}
