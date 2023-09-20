package com.decagon.domain.screen.converter;

import com.decagon.domain.screen.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class JsonConverter implements AttributeConverter<Object, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            // Handle exception or log error
            return null;
        }
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        try {
            try {
                return objectMapper.readValue(dbData, ContactInformation.class);
            } catch (Exception e) {
                try {
                    return objectMapper.readValue(dbData, GovernmentID.class);
                } catch (Exception ex1) {
                    try {
                        return objectMapper.readValue(dbData, IncomeStatus.class);
                    } catch (Exception ex2) {
                        try {
                            return objectMapper.readValue(dbData, ProofOfAddress.class);
                        } catch (Exception ex3) {
                            try {
                                return objectMapper.readValue(dbData, BankAccount.class);
                            } catch (Exception ex4) {
                                try {
                                    return objectMapper.readValue(dbData, EmploymentStatus.class);
                                } catch (Exception ex5) {
                                    return null;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Handle exception or log error
            return null;
        }
    }
}