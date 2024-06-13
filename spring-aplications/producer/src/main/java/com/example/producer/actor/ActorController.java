package com.example.producer.actor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActorController {
    private final ActorProducer producer;

    public ActorController(ActorProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/actors")
    public String createActors(@RequestParam int total) {
        for (int i = 1; i <= total; i++) {
            Actor actor = new Actor();
            actor.setId(i);
            actor.setName("Actor " + i);
            actor.setEmail("actor" + i + "@example.com");
            producer.sendMessage(actor);
        }
        return "Actors created";
    }
}
