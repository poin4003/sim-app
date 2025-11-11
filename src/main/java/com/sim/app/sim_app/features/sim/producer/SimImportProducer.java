package com.sim.app.sim_app.features.sim.producer;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.sim.app.sim_app.features.sim.v1.dto.request.CreateSimRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimImportProducer {

    private static final String TOPIC_NAME = "import-sim-topic";
    private final KafkaTemplate<String, CreateSimRequest> kafkaTemplate;

    public void sendSimToImportQueue(CreateSimRequest request) {
        log.info("Sending SIM with phone number {} to kafka topic {}",
                request.getSimPhoneNumber(), TOPIC_NAME);

        String simKey = request.getSimPhoneNumber() + "-" + UUID.randomUUID().toString();

        org.springframework.messaging.Message<CreateSimRequest> message = MessageBuilder
                                                                        .withPayload(request)
                                                                        .setHeader(KafkaHeaders.TOPIC, TOPIC_NAME)
                                                                        .setHeader(KafkaHeaders.KEY, simKey)
                                                                        .setHeader("__TypeId__", CreateSimRequest.class.getName())
                                                                        .build();

        kafkaTemplate.send(message);
    }
}
