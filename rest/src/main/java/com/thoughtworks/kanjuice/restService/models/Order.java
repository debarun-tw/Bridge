package com.thoughtworks.kanjuice.restService.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;

public class Order {

    private static final String TYPE = "type";
    private static final String CARD_ID = "cardID";
    private static final String DEVICE_ID = "deviceID";


    @Field(TYPE)
    @JsonProperty
    public String type;

    @Field(CARD_ID)
    @JsonProperty
    public String cardID;

    @Field(DEVICE_ID)
    @JsonProperty
    public String deviceID;

    public Order(){}

    public Order(String type, String deviceID, String cardID) {
        this.type = type;
        this.deviceID = deviceID;
        this.cardID = cardID;
    }

    public String getType() {
        return type;
    }

    public String getDeviceID(){
        return deviceID;
    }

    public String getCardID() {
        return cardID;
    }
}
