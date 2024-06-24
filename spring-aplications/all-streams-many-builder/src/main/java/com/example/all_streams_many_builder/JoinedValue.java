package com.example.all_streams_many_builder;

import java.time.LocalDate;

import com.example.all_streams_many_builder.Order;
import com.example.all_streams_many_builder.Trade;
import com.example.all_streams_many_builder.Actor;
import com.example.all_streams_many_builder.Participant;
import com.example.all_streams_many_builder.Asset;

public class JoinedValue {
    private String id;
    private String matchId;
    private String side;
    private int quantity;
    private double price;
    private String orderId;
    private int assetId;
    private String assetName;
    private int actorId;
    private String actorName;
    private String actorEmail;
    private int participantId;
    private String participantName;

    public JoinedValue() {
    }

    // Constructor
    public JoinedValue(Trade trade, Order order) {
        this.id = trade.getId();
        this.matchId = trade.getMatchId();
        this.side = trade.getSide();
        this.quantity = trade.getQuantity();
        this.price = trade.getPrice();
        this.orderId = trade.getOrderId();
        this.assetId = order.getAssetId();
        this.assetName = null;
        this.actorId = trade.getActorId();
        this.actorName = null;
        this.actorEmail = null;
        this.participantId = trade.getParticipantId();
        this.participantName = null;
    }

    // 
    public JoinedValue(JoinedValue joinedValue, Asset asset) {
        this.id = joinedValue.getId();
        this.matchId = joinedValue.getMatchId();
        this.side = joinedValue.getSide();
        this.quantity = joinedValue.getQuantity();
        this.price = joinedValue.getPrice();
        this.orderId = joinedValue.getOrderId();
        this.assetId = joinedValue.getAssetId();
        this.assetName = asset.getName();
        this.actorId = joinedValue.getActorId();
        this.actorName = joinedValue.getActorName();
        this.actorEmail = joinedValue.getActorEmail();
        this.participantId = joinedValue.getParticipantId();
        this.participantName = joinedValue.getParticipantName();
    }

    public JoinedValue(JoinedValue joinedValue, Actor actor) {
        this.id = joinedValue.getId();
        this.matchId = joinedValue.getMatchId();
        this.side = joinedValue.getSide();
        this.quantity = joinedValue.getQuantity();
        this.price = joinedValue.getPrice();
        this.orderId = joinedValue.getOrderId();
        this.assetId = joinedValue.getAssetId();
        this.assetName = joinedValue.getAssetName();
        this.actorId = joinedValue.getActorId();
        this.actorName = actor.getName();
        this.actorEmail = actor.getEmail();
        this.participantId = joinedValue.getParticipantId();
        this.participantName = joinedValue.getParticipantName();
    }

    public JoinedValue(JoinedValue joinedValue, Participant participant) {
        this.id = joinedValue.getId();
        this.matchId = joinedValue.getMatchId();
        this.side = joinedValue.getSide();
        this.quantity = joinedValue.getQuantity();
        this.price = joinedValue.getPrice();
        this.orderId = joinedValue.getOrderId();
        this.assetId = joinedValue.getAssetId();
        this.assetName = joinedValue.getAssetName();
        this.actorId = joinedValue.getActorId();
        this.actorName = joinedValue.getActorName();
        this.actorEmail = joinedValue.getActorEmail();
        this.participantId = joinedValue.getParticipantId();
        this.participantName = participant.getName();
    }
    


    // Getters and setters for all fields
    // JoinedValue fields
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActorEmail() {
        return actorEmail;
    }

    public void setActorEmail(String actorEmail) {
        this.actorEmail = actorEmail;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    
}
