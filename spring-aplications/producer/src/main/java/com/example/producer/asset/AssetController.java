package com.example.producer.asset;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssetController {
    private final AssetProducer producer;
    private final ValueOperations<String, String> valueOps;

    public AssetController(AssetProducer producer, StringRedisTemplate redisTemplate) {
        this.producer = producer;
        this.valueOps = redisTemplate.opsForValue();
    }

    @PostMapping("/assets")
    public String createAssets(@RequestParam int total) {
        for (int i = 1; i <= total; i++) {
            Asset asset = new Asset();
            asset.setId(i);
            asset.setName("Asset " + i);
            asset.setType("STOCK");
            asset.setPrice(50.0);
            producer.sendMessage(asset);
        }

        String totalStr = valueOps.get("MaxAssets");
        int currentTotal = totalStr != null ? Integer.parseInt(totalStr) : 0;

        if (total > currentTotal) {
            valueOps.set("MaxAssets", String.valueOf(total));
        }

        return "Assets created";
    }
}