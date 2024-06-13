package com.example.producer.participant;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParticipantController {
    private final ParticipantProducer producer;

    public ParticipantController(ParticipantProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/participants")
    public String createParticipants(@RequestParam int total) {
        for (int i = 1; i <= total; i++) {
            Participant participant = new Participant();
            participant.setId(i);
            participant.setName("Participant " + i);
            producer.sendMessage(participant);
        }
        return "Participants created";
    }
}
