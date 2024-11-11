package com.literatura.challenge_2_back_literatura.config;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literatura.challenge_2_back_literatura.config.iConfig.IConverterDados;

public class ConverterDados implements IConverterDados {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T converterDadosJsonParaJava(String json, Class<T> classe) {
        try {
            return objectMapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
