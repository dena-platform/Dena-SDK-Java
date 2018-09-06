package com.dena.client.common.utils;

import com.dena.client.common.exception.InvalidJSONException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@gmail.com>]
 */
public class JSONMapper {
    private final static ObjectMapper JSON_MAPPER = new ObjectMapper();

    static {
        JSON_MAPPER.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        JSON_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }


    public static <T> String createJSONFromObject(final T object) throws InvalidJSONException {
        try {
            return JSON_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            String errMessage = String.format("Error in converting from Class [%s] to JSON", object.getClass().getSimpleName());
            throw new InvalidJSONException(errMessage, ex);
        }
    }

    public static <T> List<T> createListObjectsFromJSON(final String jsonString, final Class<T> classType) throws InvalidJSONException {
        try {
            return JSON_MAPPER.readValue(jsonString, TypeFactory.defaultInstance().constructCollectionType(List.class, classType));
        } catch (IOException ex) {
            String errMessage = String.format("Error in converting from JSON [%s] to class [%s]", jsonString, classType);
            throw new InvalidJSONException(errMessage, ex);
        }
    }

    public static <T> T createObjectFromJSON(final String jsonString, final Class<T> classType) throws InvalidJSONException {
        try {
            return JSON_MAPPER.readValue(jsonString, classType);
        } catch (IOException ex) {
            String errorMessage = String.format("Error in converting from JSON [%s] to class [%s]", jsonString, classType);

            throw new InvalidJSONException(errorMessage, ex);
        }
    }

    public static HashMap<String, Object> createMapFromJSON(final String jsonString) throws InvalidJSONException {
        HashMap<String, Object> map;
        try {
            map = JSON_MAPPER.readValue(jsonString, new TypeReference<HashMap<String, Object>>() {
            });
            return map;
        } catch (IOException ex) {
            String errorMessage = String.format("Error in converting from JSON [%s] to class [%s]", jsonString, HashMap.class);
            throw new InvalidJSONException(errorMessage, ex);
        }

    }

}
