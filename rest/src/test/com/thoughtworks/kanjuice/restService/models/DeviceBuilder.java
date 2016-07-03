package com.thoughtworks.kanjuice.restService.models;

public class DeviceBuilder {


    private final Device device;

    public DeviceBuilder() {
        device = new Device();
    }

    public DeviceBuilder withDefaults() {
        device.deviceID = "999";
        device.outletType = "juice";
        device.location = "IN-BLR-0";
        device.gcmToken = "testToken";
        return this;
    }

    public DeviceBuilder withdeviceID(String deviceID) {
        device.deviceID = deviceID;
        return this;
    }

    public DeviceBuilder withOutletType(String outletType) {
        device.outletType = outletType;
        return this;
    }

    public DeviceBuilder withLocation(String location) {
        device.location = location;
        return this;
    }

    public DeviceBuilder withGCMToken(String gcmToken){
        device.gcmToken = gcmToken;
        return this;
    }

    public Device build() {
        return device;
    }
}
