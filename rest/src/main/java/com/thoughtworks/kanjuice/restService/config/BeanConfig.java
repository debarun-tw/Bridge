package com.thoughtworks.kanjuice.restService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    public static JsonMapper jsonMapper() {
        return new JsonMapper();
    }

    @Bean
    public JsonMapper objectMapper() {
        return jsonMapper();
    }
}