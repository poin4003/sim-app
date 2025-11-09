package com.sim.app.sim_app.core.kafka;

import java.util.Map;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sim.app.sim_app.features.sim.v1.dto.CreateSimRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@EnableKafka
@RequiredArgsConstructor
public class KafkaConsumerConfig {
    private final KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<String, CreateSimRequest> consumerFactory() { 
        Map<String, Object> configs = kafkaProperties.buildConsumerProperties(); 

        if (configs == null) {
            throw new NullPointerException("ConsumerFactory configs must not be null");
        }

        return new DefaultKafkaConsumerFactory<>(
            configs, 
            new StringDeserializer(), 
            new JsonDeserializer<>(CreateSimRequest.class)
        );
    } 

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CreateSimRequest>
        kafkaListenerContainerFactory(ConsumerFactory<String, CreateSimRequest> consumerFactory) {

        FixedBackOff fixedBackOff = new FixedBackOff(0L, 3); 

        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
            log.error("Failed to process record after 3 retries. Skipping record. Topic: {}, Offset: {}, Error: {}", 
                    consumerRecord.topic(), consumerRecord.offset(), exception.getMessage());
        }, fixedBackOff); 

        errorHandler.addNotRetryableExceptions(
            InvalidFormatException.class
        );

        ConcurrentKafkaListenerContainerFactory<String, CreateSimRequest> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        
        if (consumerFactory == null) {
            throw new NullPointerException("ConsumerFactory must not be null");
        }

        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }
}
