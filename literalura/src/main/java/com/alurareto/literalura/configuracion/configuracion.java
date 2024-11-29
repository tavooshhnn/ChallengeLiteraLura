package com.alurareto.literalura.configuracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class configuracion {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
