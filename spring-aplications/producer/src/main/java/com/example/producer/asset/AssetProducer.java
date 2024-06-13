package com.example.producer.asset;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AssetProducer {
    private static final String TOPIC = "assets";

    private final KafkaTemplate<String, Asset> kafkaTemplate;

    public AssetProducer(KafkaTemplate<String, Asset> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Asset asset) {
        String key = String.valueOf(asset.getId());
        this.kafkaTemplate.send(TOPIC, key, asset);
    }
}
