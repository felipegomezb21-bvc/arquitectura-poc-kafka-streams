package com.example.producer.actor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ActorProducer {
    private static final String TOPIC = "actors";

    private final KafkaTemplate<String, Actor> kafkaTemplate;

    public ActorProducer(KafkaTemplate<String, Actor> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Actor actor) {
        String key = String.valueOf(actor.getId());
        this.kafkaTemplate.send(TOPIC, key, actor);
    }
}
