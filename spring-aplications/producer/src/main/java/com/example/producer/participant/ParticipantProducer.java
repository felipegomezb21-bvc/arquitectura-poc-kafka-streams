package com.example.producer.participant;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ParticipantProducer {
    private static final String TOPIC = "participants";

    private final KafkaTemplate<String, Participant> kafkaTemplate;

    public ParticipantProducer(KafkaTemplate<String, Participant> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Participant participant) {
        String key = String.valueOf(participant.getId());
        this.kafkaTemplate.send(TOPIC, key, participant);
    }
}
