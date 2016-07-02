package com.thoughtworks.kanjuice.restService.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;

import java.io.IOException;

public class JsonMapper extends ObjectMapper {
    public JsonMapper() {
        super.registerModule(new JodaModule());
        super.registerModule(new JsonOrgModule());

        super.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        super.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);

        super.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        super.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public String toJson(Object object) {
        try {
            return this.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        try {
            return this.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}