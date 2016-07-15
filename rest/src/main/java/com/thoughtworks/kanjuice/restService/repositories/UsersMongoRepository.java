package com.thoughtworks.kanjuice.restService.repositories;

import com.thoughtworks.kanjuice.restService.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersMongoRepository extends MongoRepository<User, String> {
    public User findByInternalNumber(String internalNumber);
}
