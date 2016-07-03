package com.thoughtworks.kanjuice.restService.controllers;

import com.thoughtworks.kanjuice.restService.models.Device;
import com.thoughtworks.kanjuice.restService.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(value = "/device", produces = MediaType.APPLICATION_JSON_VALUE)
class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @RequestMapping(method = RequestMethod.POST)
    Device createOrUpdateDevice(@RequestBody @Valid Device device) throws IOException {
        return deviceService.createOrUpdateDevice(device);
    }

}