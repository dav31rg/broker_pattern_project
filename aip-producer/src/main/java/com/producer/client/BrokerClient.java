package com.producer.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.producer.client.dto.BrokerPublishRequest;
import com.producer.client.dto.BrokerPublishResponse;
import com.producer.client.dto.BrokerRegisterTopicRequest;

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

    public BrokerPublishResponse publish(String topicName, String payload, String producerId) {
        return restClient.post()
                .uri("/publish")
                .body(new BrokerPublishRequest(topicName, payload, producerId))
                .retrieve()
                .body(BrokerPublishResponse.class);
    }
}
