package com.thoughtworks.kanjuice.restService.services;

import com.thoughtworks.kanjuice.restService.models.User;
import com.thoughtworks.kanjuice.restService.repositories.AllUsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final AllUsers allUsers;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(AllUsers allUsers) {
        this.allUsers = allUsers;
    }

    public User findUserByCardId(String cardID){
        return allUsers.findByInternalNumber(cardID);
    }

}
