package com.sim.app.sim_app.config.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.jsonwebtoken.io.DeserializationException;

import lombok.extern.slf4j.Slf4j;

@Configuration 
@Slf4j
public class MyKafkaConfig {

    @Bean
    public DefaultErrorHandler commonErrorHandler() {
        FixedBackOff fixedBackOff = new FixedBackOff(0L, 3);

        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
            if (consumerRecord != null) {
                log.error(
                    "Failed to process record after 3 retries. Skipping record. Topic: {}, Offset: {}, Error: {}",
                    consumerRecord.topic(),
                    consumerRecord.offset(),
                    exception.getMessage()
                );
            } else {
                log.error("Kafka error occurred (no consumer record): {}", exception.getMessage());
            }
        }, fixedBackOff);

        errorHandler.addNotRetryableExceptions(
            InvalidFormatException.class,
            DeserializationException.class
        );

        return errorHandler;
    }
}
