package com.example.trade_order_simulator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Random;
import java.util.UUID;

@RestController
public class TradeOrderController {
    private final OrderProducer producerOrder;
    private final TradeProducer producerTrade;
    private final ValueOperations<String, String> valueOps;

    public TradeOrderController(OrderProducer producerOrder,TradeProducer producerTrade, StringRedisTemplate redisTemplate) {
        this.producerOrder = producerOrder;
        this.producerTrade = producerTrade;
        this.valueOps = redisTemplate.opsForValue();
    }

    @PostMapping("/trades")
    public String createTrades(@RequestParam int total) {
        for (int i = 1; i <= total; i++) {

            Order buyOrder = new Order();
            buyOrder.setId(UUID.randomUUID().toString());
            buyOrder.setStatus("OPEN");
            buyOrder.setType("BUY");
            buyOrder.setQuantity(100);
            buyOrder.setPrice(50.0);
            buyOrder.setAssetId(generateRandomIndex("MaxAssets"));
            producerOrder.sendMessage(buyOrder,"OrdersBuy");

            Order sellOrder = new Order();
            sellOrder.setId(UUID.randomUUID().toString());
            sellOrder.setStatus("OPEN");
            sellOrder.setType("SELL");
            sellOrder.setQuantity(100);
            sellOrder.setPrice(50.0);
            sellOrder.setAssetId(buyOrder.getAssetId());
            producerOrder.sendMessage(sellOrder, "OrdersSell");

            Trade buyerTrade = new Trade(); //Buyer
            buyerTrade.setId(UUID.randomUUID().toString());
            buyerTrade.setMatchId(UUID.randomUUID().toString());
            buyerTrade.setSide("BUY");
            buyerTrade.setQuantity(100);
            buyerTrade.setPrice(50.0);
            buyerTrade.setOrderId(buyOrder.getId());
            buyerTrade.setActorId(generateRandomIndex("MaxActors"));
            buyerTrade.setParticipantId(generateRandomIndex("MaxParticipants"));
            producerTrade.sendMessage(buyerTrade,"TradesBuy");

            Trade sellerTrade = new Trade(); // Seller
            sellerTrade.setId(UUID.randomUUID().toString());
            sellerTrade.setMatchId(buyerTrade.getMatchId());
            sellerTrade.setSide("SELL");
            sellerTrade.setQuantity(100);
            sellerTrade.setPrice(50.0);
            sellerTrade.setOrderId(sellOrder.getId());
            sellerTrade.setActorId(generateRandomIndex("MaxActors"));
            sellerTrade.setParticipantId(generateRandomIndex("MaxParticipants"));
            producerTrade.sendMessage(sellerTrade, "TradesSell");
        }
        return "Trades created";
    }

    private int generateRandomIndex(String key) {
        String totalStr = valueOps.get(key);
        int randomIndex = totalStr != null ? 1 + new Random().nextInt(Integer.parseInt(totalStr) - 1): 1 + new Random().nextInt(10) ;
        
        return randomIndex;
    }
}
