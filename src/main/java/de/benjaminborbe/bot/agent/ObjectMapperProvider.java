package de.benjaminborbe.bot.agent;

import javax.inject.Provider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperProvider implements Provider<ObjectMapper> {

  @Override
  public ObjectMapper get() {
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.registerModule(new Jdk8Module());
    objectMapper.registerModule(createModule());
    objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    return objectMapper;
  }

  private Module createModule() {
    final SimpleModule mappingModule = new SimpleModule("mappingModule");
    return mappingModule;
  }
}
