package com.hunus.playerprofileservice.converter;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class MapConverter extends GenericConverter<Map<String, Integer>> {

    public MapConverter() {
        this.objectMapper = new ObjectMapper();
    }

    public MapConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
