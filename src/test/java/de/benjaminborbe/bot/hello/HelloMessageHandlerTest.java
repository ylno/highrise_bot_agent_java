package de.benjaminborbe.bot.hello;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;

import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.agent.RequestUser;
import de.benjaminborbe.bot.agent.Response;

public class HelloMessageHandlerTest {

  @Test
  public void testHandleMessageReturnNotNullResult() throws Exception {
    final HighriseHandler highriseHandler = new HighriseHandler();
    final Request request = new Request();
    request.setBot("MyBot");
    request.setMessage("hello MyBot");
    final Collection<Response> responses = highriseHandler.HandleMessage(request);
    assertThat(responses, is(notNullValue()));
  }

  @Test
  public void testHandleMessageReturnOneMessageIfPatternMatches() throws Exception {
    final HighriseHandler highriseHandler = new HighriseHandler();
    final Request request = new Request();
    request.setBot("MyBot");
    request.setMessage("hello MyBot");
    final Collection<Response> responses = highriseHandler.HandleMessage(request);
    assertThat(responses, is(notNullValue()));
    assertThat(responses.size(), is(1));
  }

  @Test
  public void testHandleMessageReturnNoMessageIfPatternNotMatches() throws Exception {
    final HighriseHandler highriseHandler = new HighriseHandler();
    final Request request = new Request();
    request.setBot("MyBot");
    request.setMessage("hello foo");
    final Collection<Response> responses = highriseHandler.HandleMessage(request);
    assertThat(responses, is(notNullValue()));
    assertThat(responses.size(), is(0));
  }

  @Test
  public void testHandleMessageReturnMessageHelloWithoutUser() throws Exception {
    final HighriseHandler highriseHandler = new HighriseHandler();
    final Request request = new Request();
    request.setBot("MyBot");
    request.setMessage("hello MyBot");
    final Collection<Response> responses = highriseHandler.HandleMessage(request);
    assertThat(responses, is(notNullValue()));
    assertThat(responses.size(), is(1));
    final Response response = responses.iterator().next();
    assertThat(response, is(notNullValue()));
    assertThat(response.getMessage(), is("hello from java"));
  }

  @Test
  public void testHandleMessageReturnMessageHelloWithUser() throws Exception {
    final HighriseHandler highriseHandler = new HighriseHandler();
    final Request request = new Request();
    request.setBot("MyBot");
    request.setMessage("hello MyBot");
    final RequestUser from = new RequestUser();
    from.setUsername("tester");
    request.setFrom(from);
    final Collection<Response> responses = highriseHandler.HandleMessage(request);
    assertThat(responses, is(notNullValue()));
    assertThat(responses.size(), is(1));
    final Response response = responses.iterator().next();
    assertThat(response, is(notNullValue()));
    assertThat(response.getMessage(), is("hello tester from java"));
  }
}
