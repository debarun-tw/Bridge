package com.thoughtworks.kanjuice.restService.services;

import com.thoughtworks.kanjuice.restService.models.Device;
import com.thoughtworks.kanjuice.restService.repositories.AllDevices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DeviceService {

    private final AllDevices allDevices;
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);

    @Autowired
    public DeviceService(AllDevices allDevices) {
        this.allDevices = allDevices;
    }

    public Device createOrUpdateDevice(Device device) {
        Device existingDevice = allDevices.findByDeviceID(device.getDeviceID());

        if(existingDevice == null){
            LOGGER.info("Creating New Device with device ID: ", device.getDeviceID());
            return allDevices.insert(device);
        }
        else {
            LOGGER.info("Updating Device with device ID: ", device.getDeviceID());
            existingDevice.updateDevice(device);
            return allDevices.save(existingDevice);
        }
    }

}
