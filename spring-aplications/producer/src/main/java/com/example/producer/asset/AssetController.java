package com.example.producer.asset;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssetController {
    private final AssetProducer producer;

    public AssetController(AssetProducer producer) {
        this.producer = producer;
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
        return "Assets created";
    }
}