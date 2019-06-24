package com.neticon.neticon.tools;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class JsonUtils {
    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return (T) objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T fromJson(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    public static <T> T fromJson(String json, TypeReference<T> type) {
        if (json == null) {
            return null;
        }
        try {
            return (T) objectMapper.readValue(json, type);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}

