package com.thoughtworks.kanjuice.restService.models;

import com.jayway.jsonpath.JsonPath;
import com.thoughtworks.kanjuice.restService.config.JsonMapper;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DeviceTest {

    @Test
    public void shouldSerialize() throws Exception {
        Device device = new DeviceBuilder().withDefaults().build();
        String json = new JsonMapper().toJson(device);

        assertThat(JsonPath.read(json, "$.deviceID").toString(), is(device.getDeviceID()));
        assertThat(JsonPath.read(json, "$.outletType").toString(), is(device.getOutletType()));
    }

    @Test
    public void shouldUpdateDevice() throws Exception {
        Device existingDevice = new DeviceBuilder().withdeviceID("999").withDefaults().build();
        Device updateToDevice = new DeviceBuilder().withDefaults().withGCMToken("newToken").build();

        existingDevice.updateDevice(updateToDevice);

        assertThat(existingDevice.deviceID, is("999"));
        assertThat(existingDevice.gcmToken, is("newToken"));

    }

    @Test
    public void shouldNotUpdateDeviceIfEmptyFields() throws Exception {
        Device existingDevice = new DeviceBuilder().withDefaults().withdeviceID("999").build();
        Device updateToDevice = new DeviceBuilder().withDefaults().withdeviceID("").build();

        existingDevice.updateDevice(updateToDevice);

        assertThat(existingDevice.deviceID, is("999"));

    }
}