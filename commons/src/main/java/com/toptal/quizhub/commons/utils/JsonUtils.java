package com.toptal.quizhub.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toptal.quizhub.commons.exceptions.JsonToStringFailedException;
import com.toptal.quizhub.commons.exceptions.StringToJsonFailedException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String getJsonString(Object object) {

        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonToStringFailedException();
        }
    }

    public static JsonNode mapToJson(String json) {

        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            throw new StringToJsonFailedException();
        }
    }
}
