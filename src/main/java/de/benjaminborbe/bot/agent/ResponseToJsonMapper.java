package de.benjaminborbe.bot.agent;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseToJsonMapper {

  private final ObjectMapper objectMapper;

  @Inject
  public ResponseToJsonMapper(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public byte[] map(final Response response) throws JsonProcessingException {
    return objectMapper.writeValueAsBytes(response);
  }

}
