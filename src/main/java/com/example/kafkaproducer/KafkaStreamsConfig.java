package com.example.kafkaproducer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaStreamsConfig {

    @Value("${spring.kafka.properties.bootstrap.servers}")
    private String bootstrapServers;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfig(KafkaProperties properties) {
        Map<String, Object> configs = new HashMap<>();

        configs.putAll(properties.buildAdminProperties());
        configs.putAll(properties.buildConsumerProperties());
        configs.putAll(properties.buildProducerProperties());
        configs.putAll(properties.buildStreamsProperties());

        configs.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        configs.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(StreamsConfig.APPLICATION_ID_CONFIG, "spring-boot-producer");
        configs.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        return new KafkaStreamsConfiguration(configs);
    }
}
