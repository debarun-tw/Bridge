package com.thoughtworks.kanjuice.restService.services;

import com.thoughtworks.kanjuice.restService.models.Device;
import com.thoughtworks.kanjuice.restService.models.DeviceBuilder;
import com.thoughtworks.kanjuice.restService.repositories.AllDevices;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeviceServiceTest {

    @Mock
    private Device device;
    @Mock
    private AllDevices allDevices;

    private DeviceService deviceService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        deviceService = new DeviceService(allDevices);
    }

    @Test
    public void shouldCheckForExistingDeviceBeforeCreating() throws Exception {
        String existingID  = "ID";
        when(device.getDeviceID()).thenReturn(existingID);

        deviceService.createOrUpdateDevice(device);

        verify(allDevices).findByDeviceID(existingID);

    }

    @Test
    public void shouldCreateDeviceIfNotExisting() throws Exception {
        String deviceID = "ID";
        when(device.getDeviceID()).thenReturn(deviceID);
        when(allDevices.findByDeviceID(deviceID)).thenReturn(null);

        deviceService.createOrUpdateDevice(device);

        verify(allDevices).insert(device);
    }

    @Test
    public void shouldUpdateExistingDevice() throws Exception {
        String existingID  = "ID";
        Device updateToDevice = new DeviceBuilder().withDefaults().withdeviceID(existingID).withGCMToken("newToken").build();
        when(device.getDeviceID()).thenReturn(existingID);
        when(allDevices.findByDeviceID(existingID)).thenReturn(device);

        deviceService.createOrUpdateDevice(updateToDevice);

        verify(allDevices).save(any(Device.class));
    }
}