package com.example.producer.participant;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParticipantController {
    private final ParticipantProducer producer;
    private final ValueOperations<String, String> valueOps;

    public ParticipantController(ParticipantProducer producer, StringRedisTemplate redisTemplate) {
        this.producer = producer;
        this.valueOps = redisTemplate.opsForValue();
    }

    @PostMapping("/participants")
    public String createParticipants(@RequestParam int total) {
        for (int i = 1; i <= total; i++) {
            Participant participant = new Participant();
            participant.setId(i);
            participant.setName("Participant " + i);
            producer.sendMessage(participant);
        }

        // Get the current total from Redis
        String totalStr = valueOps.get("MaxParticipants");
        int currentTotal = totalStr != null ? Integer.parseInt(totalStr) : 0;

        // Only update the total in Redis if the new total is greater
        if (total > currentTotal) {
            valueOps.set("MaxParticipants", String.valueOf(total));
        }


        return "Participants created";
    }
}
