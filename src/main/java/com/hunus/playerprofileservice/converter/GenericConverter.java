package com.hunus.playerprofileservice.converter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GenericConverter<T> implements AttributeConverter<T, String> {
    protected static final Logger logger = LoggerFactory.getLogger(GenericConverter.class);
    protected ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(T input) {
        String result = null;
        try {
            result = objectMapper.writeValueAsString(input);
        } catch (final JsonProcessingException ex) {
            logger.error("Error when writing JSON", ex);
        }
        return result;
    }

    @Override
    public T convertToEntityAttribute(String input) {
        T result = null;
        try {
            result = objectMapper.readValue(input, getParameterClass());
        } catch (final IOException ex) {
            logger.error("Error when reading JSON", ex);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getParameterClass() {
        Object argumentType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (argumentType instanceof ParameterizedType) {
            return (Class<T>) ((ParameterizedType) argumentType).getRawType();
        } else {
            return (Class<T>) argumentType;
        }
    }
}
