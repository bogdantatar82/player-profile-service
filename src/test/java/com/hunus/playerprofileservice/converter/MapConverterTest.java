package com.hunus.playerprofileservice.converter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapConverterTest {

    @Test
    void shouldConvertToDatabaseColumn() {
        // given
        MapConverter converter = new MapConverter();
        Map<String, Integer> inputValue = Map.of(
            "cash", 100,
            "coins", 123,
            "item_1", 1,
            "item_34", 3,
            "item_55", 2
        );

        // when
        String convertedValue = converter.convertToDatabaseColumn(inputValue);

        // then
        assertTrue(convertedValue.contains("\"cash\":100"));
        assertTrue(convertedValue.contains("\"coins\":123"));
        assertTrue(convertedValue.contains("\"item_1\":1"));
        assertTrue(convertedValue.contains("\"item_34\":3"));
        assertTrue(convertedValue.contains("\"item_55\":2"));
    }

    @Test
    void shouldNotConvertToDatabaseColumn() throws Exception {
        // given
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        when(objectMapper.writeValueAsString(anyList())).thenThrow(JsonProcessingException.class);
        MapConverter converter = new MapConverter(objectMapper);

        // when
        String convertedValue = converter.convertToDatabaseColumn(Map.of());

        // then
        assertNull(convertedValue);
    }

    @Test
    void shouldConvertToEntityAttribute() {
        // given
        MapConverter converter = new MapConverter();
        String inputValue = "{\"cash\":100,\"coins\":123,\"item_1\":1,\"item_34\":3,\"item_55\":2}";

        // when
        Map<String, Integer> entityValue = converter.convertToEntityAttribute(inputValue);

        // then
        assertEquals(100, entityValue.get("cash"));
        assertEquals(123, entityValue.get("coins"));
        assertEquals(1, entityValue.get("item_1"));
        assertEquals(3, entityValue.get("item_34"));
        assertEquals(2, entityValue.get("item_55"));
    }

    @Test
    void shouldNotConvertToEntityAttribute() throws Exception {
        // given
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        when(objectMapper.readValue(anyString(), any(TypeReference.class))).thenThrow(JsonProcessingException.class);
        MapConverter converter = new MapConverter(objectMapper);

        // when
        Map<String, Integer> entityValue = converter.convertToEntityAttribute("{}");

        // then
        assertNull(entityValue);
    }
}
