package com.framework.auth.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Auther: heyinbo
 * @Date: 2018/6/13 16:19
 * @Description:
 */
public class JsonUtil {

    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T getEntity(String jsonString, Class<T> prototype) {

        objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
        try {
            return objectMapper.readValue(jsonString, prototype);
        } catch (IOException e) {
            logger.error(">>parse json to obj error.", e);
        }

        return null;
    }

    public static <T> String writeEntity(T t) {

        try {
            return objectMapper.writeValueAsString(t);
        } catch (IOException e) {
            logger.error(">>obj to json error.", e);
        }

        return null;
    }

}
