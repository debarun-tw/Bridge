package com.thoughtworks.kanjuice.restService.repositories;

import com.thoughtworks.kanjuice.restService.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AllUsers extends AbstractMongoRepository<User, String, UsersMongoRepository> implements UsersMongoRepository{

    private final MongoTemplate mongoTemplate;

    @Autowired
    public AllUsers(UsersMongoRepository repository, MongoTemplate mongoTemplate) {
        super(repository);
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public User findByInternalNumber(String internalNumber) {
        return repository.findByInternalNumber(internalNumber);
    }
}
