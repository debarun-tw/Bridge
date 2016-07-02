package com.thoughtworks.kanjuice.restService.repositories;

import com.thoughtworks.kanjuice.restService.models.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DevicesMongoRepository extends MongoRepository<Device, String> {
    public Device findByDeviceID(String deviceID);
}
