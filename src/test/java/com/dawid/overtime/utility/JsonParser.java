package com.dawid.overtime.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonParser {
    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            System.out.println(objectMapper.writeValueAsString(obj));
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
