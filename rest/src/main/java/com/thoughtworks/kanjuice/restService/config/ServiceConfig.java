package com.thoughtworks.kanjuice.restService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Value("${service.title}")
    private String title;

    public String getTitle() {
        return title;
    }

}
