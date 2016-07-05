package com.thoughtworks.kanjuice.restService;

import org.joda.time.DateTimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@EnableWebMvc
@SpringBootApplication
@EnableMongoRepositories("com.thoughtworks.kanjuice")
@EnableMongoAuditing
@EnableAutoConfiguration
@ComponentScan("com.thoughtworks.kanjuice")
public class Application  {
    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException {

        DateTimeZone.setDefault(DateTimeZone.UTC);
        SpringApplication.run(Application.class, args);
    }
}