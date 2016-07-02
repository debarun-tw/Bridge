package com.thoughtworks.kanjuice.restService.repositories;

import com.thoughtworks.kanjuice.restService.models.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AllDevices extends AbstractMongoRepository<Device, String, DevicesMongoRepository> implements DevicesMongoRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public AllDevices(DevicesMongoRepository repository, MongoTemplate mongoTemplate) {
        super(repository);
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Device findByDeviceID(String deviceID) {
        return repository.findByDeviceID(deviceID);
    }
}
