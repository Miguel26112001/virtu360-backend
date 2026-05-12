package com.example.virtu360.shared.infrastructure.documentation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Primary
public class JacksonConfig {

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();

    Hibernate6Module hibernateModule = new Hibernate6Module();

    hibernateModule.configure(Hibernate6Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
    hibernateModule.configure(Hibernate6Module.Feature.FORCE_LAZY_LOADING, false);

    JavaTimeModule javaTimeModule = new JavaTimeModule();

    mapper.registerModule(hibernateModule);
    mapper.registerModule(javaTimeModule);

    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    return mapper;
  }
}
