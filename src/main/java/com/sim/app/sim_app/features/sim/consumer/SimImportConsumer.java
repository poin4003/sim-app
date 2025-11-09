package com.sim.app.sim_app.features.sim.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.sim.app.sim_app.features.sim.v1.dto.CreateSimRequest;
import com.sim.app.sim_app.features.sim.v1.dto.SimResponse;
import com.sim.app.sim_app.features.sim.v1.service.SimService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimImportConsumer {

    private final SimService simService;

    @KafkaListener(
        topics = "import-sim-topic", 
        groupId = "sim-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void ImportSingleSim(CreateSimRequest request) {
        log.info("Received created Sim: {}", request);

        try {
            SimResponse response = simService.createSim(request);
            log.info("Successfully created SIM: {}", response.getSimPhoneNumber());
        } catch (Exception e) {
            log.error("Error processing SIM import for phone number {}: {}", request.getSimPhoneNumber(), e.getMessage());
        }
    }
}
