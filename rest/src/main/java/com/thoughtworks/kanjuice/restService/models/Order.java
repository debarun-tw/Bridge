package com.thoughtworks.kanjuice.restService.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;

public class Order {

    private static final String TYPE = "type";
    private static final String USER_ID = "userID";
    private static final String DEVICE_ID = "deviceID";


    @Field(TYPE)
    @JsonProperty
    public String type;

    @Field(USER_ID)
    @JsonProperty
    public String userID;

    @Field(DEVICE_ID)
    @JsonProperty
    public String deviceID;

    public Order(){}

    public Order(String type, String deviceID, String userID) {
        this.type = type;
        this.deviceID = deviceID;
        this.userID = userID;
    }

    public String getType() {
        return type;
    }

    public String getDeviceID(){
        return deviceID;
    }

    public String getUserID() {
        return userID;
    }
}
