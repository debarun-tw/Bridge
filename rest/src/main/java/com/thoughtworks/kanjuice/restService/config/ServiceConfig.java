package com.thoughtworks.kanjuice.restService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Value("${service.title}")
    public String title;

    @Value("${service.gcm.url}")
    public String gcmURL;

    @Value("${service.gcm.authKey}")
    public String gcmAuthorizationKey;

    public String getTitle() {
        return title;
    }

    public String getGcmURL() {
        return gcmURL;
    }

    public String getAuthKey() {
        return gcmAuthorizationKey;
    }
}
