package com.manibala.framework.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.mapper.factory.DefaultJackson2ObjectMapperFactory;

public class PayloadUtil {
    public static ObjectMapper defaultMapper() {
        DefaultJackson2ObjectMapperFactory defaultJackson2ObjectMapperFactory = new DefaultJackson2ObjectMapperFactory();
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = defaultJackson2ObjectMapperFactory.create(null, null);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        return objectMapper;
    }
}
