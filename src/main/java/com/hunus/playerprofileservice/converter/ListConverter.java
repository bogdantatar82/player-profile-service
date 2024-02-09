package com.hunus.playerprofileservice.converter;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ListConverter extends GenericConverter<List<String>> {
    public ListConverter() {
        this.objectMapper = new ObjectMapper();
    }

    public ListConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}