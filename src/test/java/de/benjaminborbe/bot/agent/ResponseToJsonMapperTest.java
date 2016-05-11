package de.benjaminborbe.bot.agent;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseToJsonMapperTest {

  @Test
  public void testMapMessage() throws Exception {
    final ObjectMapperProvider objectMapperProvider = new ObjectMapperProvider();
    final ObjectMapper objectMapper = objectMapperProvider.get();
    final ResponseToJsonMapper responseToJsonMapper = new ResponseToJsonMapper(objectMapper);
    final Response response = new Response();
    response.setMessage("hello");
    final byte[] map = responseToJsonMapper.map(response);
    final Response value = objectMapper.readValue(map, Response.class);
    assertThat(value.getMessage(), is("hello"));
  }

  @Test
  public void testMapRelayTrue() throws Exception {
    final ObjectMapperProvider objectMapperProvider = new ObjectMapperProvider();
    final ObjectMapper objectMapper = objectMapperProvider.get();
    final ResponseToJsonMapper responseToJsonMapper = new ResponseToJsonMapper(objectMapper);
    final Response response = new Response();
    response.setReplay(true);
    final byte[] map = responseToJsonMapper.map(response);
    final Response value = objectMapper.readValue(map, Response.class);
    assertThat(value.getReplay(), is(true));
  }

  @Test
  public void testMapRelayFalse() throws Exception {
    final ObjectMapperProvider objectMapperProvider = new ObjectMapperProvider();
    final ObjectMapper objectMapper = objectMapperProvider.get();
    final ResponseToJsonMapper responseToJsonMapper = new ResponseToJsonMapper(objectMapper);
    final Response response = new Response();
    response.setReplay(false);
    final byte[] map = responseToJsonMapper.map(response);
    final Response value = objectMapper.readValue(map, Response.class);
    assertThat(value.getReplay(), is(false));
  }
}
