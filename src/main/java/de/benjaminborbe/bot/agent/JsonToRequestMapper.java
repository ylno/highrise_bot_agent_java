package de.benjaminborbe.bot.agent;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonToRequestMapper {

  private final ObjectMapper objectMapper;

  @Inject
  public JsonToRequestMapper(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public Request map(final byte[] json) throws IOException {
    return objectMapper.readValue(json, Request.class);
  }

}
