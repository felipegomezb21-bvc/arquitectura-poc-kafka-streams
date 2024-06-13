package com.example.producer.actor;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActorController {
    private final ActorProducer producer;
    private final ValueOperations<String, String> valueOps;


    public ActorController(ActorProducer producer, StringRedisTemplate redisTemplate) {
        this.producer = producer;
        this.valueOps = redisTemplate.opsForValue();
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

        String totalStr = valueOps.get("MaxActors");
        int currentTotal = totalStr != null ? Integer.parseInt(totalStr) : 0;

        if (total > currentTotal) {
            valueOps.set("MaxActors", String.valueOf(total));
        }

        return "Actors created";
    }
}
