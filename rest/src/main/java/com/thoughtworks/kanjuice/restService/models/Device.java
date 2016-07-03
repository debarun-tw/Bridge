package com.thoughtworks.kanjuice.restService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = Device.COLLECTION_NAME)
public class Device {

    public static final String COLLECTION_NAME = "devices";
    public static final String DEVICE_ID = "deviceID";
    public static final String LOCATION = "location";
    public static final String GCM_TOKEN = "GCMToken";
    public static final String OUTLET_TYPE = "outletType";


    @Id
    @JsonIgnore
    private String id;

    @JsonProperty
    @Field(DEVICE_ID)
    String deviceID;

    @JsonProperty
    @Field(LOCATION)
    String location;

    @JsonProperty
    @Field(GCM_TOKEN)
    String gcmToken;

    @JsonProperty
    @Field(OUTLET_TYPE)
    String outletType;

    public Device(){
    }

    private Device(String deviceID, String location, String gcmToken, String outletType) {
        this.deviceID = deviceID;
        this.location = location;
        this.gcmToken = gcmToken;
        this.outletType = outletType;
    }

    public void updateDevice(Device requestedDevice){
        this.gcmToken = requestedDevice.gcmToken;
        this.location = requestedDevice.location;
        this.outletType = requestedDevice.outletType;
    }



    public String getDeviceID() { return deviceID;}

    public String getLocation() {
        return location;
    }

    public String getGcmToken() {
        return gcmToken;
    }

    public String getOutletType() {
        return outletType;
    }

}
