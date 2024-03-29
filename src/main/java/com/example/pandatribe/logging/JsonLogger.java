package com.example.pandatribe.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@AllArgsConstructor
public class JsonLogger {
    private final ObjectMapper objectMapper;

    public void println(Object object) {
        try {
            String json = objectMapper.writeValueAsString(object);
            System.out.println(json);
        }catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
}
