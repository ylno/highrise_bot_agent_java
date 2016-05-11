package de.benjaminborbe.bot.agent;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class JsonToRequestMapperTest {

  @Test
  public void testMapMessage() throws Exception {
    final ObjectMapperProvider objectMapperProvider = new ObjectMapperProvider();
    final JsonToRequestMapper responseToJsonMapper = new JsonToRequestMapper(objectMapperProvider.get());
    final Request request = responseToJsonMapper.map("{\"message\":\"hello\"}".getBytes("UTF-8"));
    assertThat(request, is(notNullValue()));
    assertThat(request.getMessage(), is("hello"));
  }

  @Test
  public void testMapBot() throws Exception {
    final ObjectMapperProvider objectMapperProvider = new ObjectMapperProvider();
    final JsonToRequestMapper responseToJsonMapper = new JsonToRequestMapper(objectMapperProvider.get());
    final Request request = responseToJsonMapper.map("{\"bot\":\"mybot\"}".getBytes("UTF-8"));
    assertThat(request, is(notNullValue()));
    assertThat(request.getBot(), is("mybot"));
  }

  @Test
  public void testMapAuthToken() throws Exception {
    final ObjectMapperProvider objectMapperProvider = new ObjectMapperProvider();
    final JsonToRequestMapper responseToJsonMapper = new JsonToRequestMapper(objectMapperProvider.get());
    final Request request = responseToJsonMapper.map("{\"authToken\":\"123\"}".getBytes("UTF-8"));
    assertThat(request, is(notNullValue()));
    assertThat(request.getAuthToken(), is("123"));
  }

  @Test
  public void testMapPrivateTrue() throws Exception {
    final ObjectMapperProvider objectMapperProvider = new ObjectMapperProvider();
    final JsonToRequestMapper responseToJsonMapper = new JsonToRequestMapper(objectMapperProvider.get());
    final Request request = responseToJsonMapper.map("{\"private\":\"true\"}".getBytes("UTF-8"));
    assertThat(request, is(notNullValue()));
    assertThat(request.getPriv(), is(true));
  }

  @Test
  public void testMapPrivateFalse() throws Exception {
    final ObjectMapperProvider objectMapperProvider = new ObjectMapperProvider();
    final JsonToRequestMapper responseToJsonMapper = new JsonToRequestMapper(objectMapperProvider.get());
    final Request request = responseToJsonMapper.map("{\"private\":\"false\"}".getBytes("UTF-8"));
    assertThat(request, is(notNullValue()));
    assertThat(request.getPriv(), is(false));
  }

  @Test
  public void testMapTicket() throws Exception {
    final ObjectMapperProvider objectMapperProvider = new ObjectMapperProvider();
    final JsonToRequestMapper responseToJsonMapper = new JsonToRequestMapper(objectMapperProvider.get());
    final Request request = responseToJsonMapper.map("{\"ticket\":\"123\"}".getBytes("UTF-8"));
    assertThat(request, is(notNullValue()));
    assertThat(request.getTicket(), is("123"));
  }

  @Test
  public void testMapFromDisplayname() throws Exception {
    final ObjectMapperProvider objectMapperProvider = new ObjectMapperProvider();
    final JsonToRequestMapper responseToJsonMapper = new JsonToRequestMapper(objectMapperProvider.get());
    final Request request = responseToJsonMapper.map("{\"from\":{\"displayname\":\"Hello World\"}}".getBytes("UTF-8"));
    assertThat(request, is(notNullValue()));
    assertThat(request.getFrom().getDisplayname(), is("Hello World"));
  }

  @Test
  public void testMapFromId() throws Exception {
    final ObjectMapperProvider objectMapperProvider = new ObjectMapperProvider();
    final JsonToRequestMapper responseToJsonMapper = new JsonToRequestMapper(objectMapperProvider.get());
    final Request request = responseToJsonMapper.map("{\"from\":{\"id\":\"123\"}}".getBytes("UTF-8"));
    assertThat(request, is(notNullValue()));
    assertThat(request.getFrom().getId(), is("123"));
  }

  @Test
  public void testMapFromTester() throws Exception {
    final ObjectMapperProvider objectMapperProvider = new ObjectMapperProvider();
    final JsonToRequestMapper responseToJsonMapper = new JsonToRequestMapper(objectMapperProvider.get());
    final Request request = responseToJsonMapper.map("{\"from\":{\"username\":\"tester\"}}".getBytes("UTF-8"));
    assertThat(request, is(notNullValue()));
    assertThat(request.getFrom().getUsername(), is("tester"));
  }
}
