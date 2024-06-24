package com.example.all_streams;

import java.time.LocalDate;

import com.example.all_streams.JoinedValue;


public class EnrichedTrade {
    private String id;
    private int quantity;
    private double price;
    private int assetId;
    private String assetName;
    private String orderIdBuy;
    private int actorIdBuy;
    private String actorNameBuy;
    private String actorEmailBuy;
    private int participantIdBuy;
    private String participantNameBuy;
    private String orderIdSell;
    private int actorIdSell;
    private String actorNameSell;
    private String actorEmailSell;
    private int participantIdSell;
    private String participantNameSell;


    public EnrichedTrade() {
    }

    // Constructor
    public EnrichedTrade(JoinedValue buySide, JoinedValue sellSide) {
        this.id = buySide.getId();
        this.quantity = buySide.getQuantity();
        this.price = buySide.getPrice();
        this.assetId = buySide.getAssetId();
        this.assetName = buySide.getAssetName();
        this.orderIdBuy = buySide.getOrderId();
        this.actorIdBuy = buySide.getActorId();
        this.actorNameBuy = buySide.getActorName();
        this.actorEmailBuy = buySide.getActorEmail();
        this.participantIdBuy = buySide.getParticipantId();
        this.participantNameBuy = buySide.getParticipantName();
        this.orderIdSell = sellSide.getOrderId();
        this.actorIdSell = sellSide.getActorId();
        this.actorNameSell = sellSide.getActorName();
        this.actorEmailSell = sellSide.getActorEmail();
        this.participantIdSell = sellSide.getParticipantId();
        this.participantNameSell = sellSide.getParticipantName();
    }

    // Getter and Setter methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getOrderIdBuy() {
        return orderIdBuy;
    }

    public void setOrderIdBuy(String orderIdBuy) {
        this.orderIdBuy = orderIdBuy;
    }

    public int getActorIdBuy() {
        return actorIdBuy;
    }

    public void setActorIdBuy(int actorIdBuy) {
        this.actorIdBuy = actorIdBuy;
    }

    public String getActorNameBuy() {
        return actorNameBuy;
    }

    public void setActorNameBuy(String actorNameBuy) {
        this.actorNameBuy = actorNameBuy;
    }

    public String getActorEmailBuy() {
        return actorEmailBuy;
    }

    public void setActorEmailBuy(String actorEmailBuy) {
        this.actorEmailBuy = actorEmailBuy;
    }

    public int getParticipantIdBuy() {
        return participantIdBuy;
    }

    public void setParticipantIdBuy(int participantIdBuy) {
        this.participantIdBuy = participantIdBuy;
    }

    public String getParticipantNameBuy() {
        return participantNameBuy;
    }

    public void setParticipantNameBuy(String participantNameBuy) {
        this.participantNameBuy = participantNameBuy;
    }

    public String getOrderIdSell() {
        return orderIdSell;
    }

    public void setOrderIdSell(String orderIdSell) {
        this.orderIdSell = orderIdSell;
    }

    public int getActorIdSell() {
        return actorIdSell;
    }

    public void setActorIdSell(int actorIdSell) {
        this.actorIdSell = actorIdSell;
    }

    public String getActorNameSell() {
        return actorNameSell;
    }

    public void setActorNameSell(String actorNameSell) {
        this.actorNameSell = actorNameSell;
    }

    public String getActorEmailSell() {
        return actorEmailSell;
    }

    public void setActorEmailSell(String actorEmailSell) {
        this.actorEmailSell = actorEmailSell;
    }

    public int getParticipantIdSell() {
        return participantIdSell;
    }

    public void setParticipantIdSell(int participantIdSell) {
        this.participantIdSell = participantIdSell;
    }

    public String getParticipantNameSell() {
        return participantNameSell;
    }

    public void setParticipantNameSell(String participantNameSell) {
        this.participantNameSell = participantNameSell;
    }
    
}
