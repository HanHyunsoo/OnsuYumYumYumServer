package com.onsuyum.common.util;

import java.util.Arrays;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String SPLIT_CHAR = ";!";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return String.join(SPLIT_CHAR, attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData.isBlank()) {
            return null;
        } else {
            return Arrays.asList(dbData.split(SPLIT_CHAR));
        }
    }
}
