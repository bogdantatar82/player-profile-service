package com.hunus.playerprofileservice.converter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ListConverterTest {
    @Test
    void shouldConvertToDatabaseColumn() {
        // given
        ListConverter converter = new ListConverter();
        List<String> inputValue = List.of("campaign_1", "campaign_2");

        // when
        String convertedValue = converter.convertToDatabaseColumn(inputValue);

        // then
        assertEquals("[\"campaign_1\",\"campaign_2\"]", convertedValue);
    }

    @Test
    void shouldNotConvertToDatabaseColumn() throws Exception {
        // given
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        when(objectMapper.writeValueAsString(anyList())).thenThrow(JsonProcessingException.class);
        ListConverter converter = new ListConverter(objectMapper);

        // when
        String convertedValue = converter.convertToDatabaseColumn(List.of());

        // then
        assertNull(convertedValue);
    }

    @Test
    void shouldConvertToEntityAttribute() {
        // given
        ListConverter converter = new ListConverter();
        String inputValue = "[\"campaign_1\",\"campaign_2\"]";

        // when
        List<String> entityValue = converter.convertToEntityAttribute(inputValue);

        // then
        assertEquals(List.of("campaign_1", "campaign_2"), entityValue);
    }

    @Test
    void shouldNotConvertToEntityAttribute() throws Exception {
        // given
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        when(objectMapper.readValue(anyString(), any(TypeReference.class))).thenThrow(JsonProcessingException.class);
        ListConverter converter = new ListConverter(objectMapper);

        // when
        List<String> entityValue = converter.convertToEntityAttribute("[]");

        // then
        assertNull(entityValue);
    }
}
