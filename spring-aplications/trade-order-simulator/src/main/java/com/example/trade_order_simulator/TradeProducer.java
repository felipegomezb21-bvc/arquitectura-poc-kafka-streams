package com.example.trade_order_simulator;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TradeProducer {

    private final KafkaTemplate<String, Trade> kafkaTemplate;

    public TradeProducer(KafkaTemplate<String, Trade> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Trade trade, String topic) {
        String key = String.valueOf(trade.getId());
        this.kafkaTemplate.send(topic, key, trade);
    }
}
