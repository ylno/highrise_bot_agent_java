package de.benjaminborbe.bot.hello;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Test;

import com.algaworks.highrisehq.HighriseException;

import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.agent.Response;
import de.benjaminborbe.bot.highrise.Credentials;

public class HighriseHandlerTest {

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
  public void testHandleMessageUser() throws Exception {
    final HighriseHandler highriseHandler = new HighriseHandler();
    final Request request = new Request();
    request.setBot("MyBot");
    request.setMessage("/highrise user xyz");
    final Collection<Response> responses = highriseHandler.HandleMessage(request);
    assertThat(responses, is(notNullValue()));
    assertThat(responses.size(), is(1));
    assertThat(responses.iterator().next().getMessage(), is("ok, user is xyz"));
  }

  @Test
  public void testHandleMessagePass() throws Exception {
    final HighriseHandler highriseHandler = new HighriseHandler();
    final Request request = new Request();
    request.setBot("MyBot");
    request.setMessage("/highrise pass xyy");
    final Collection<Response> responses = highriseHandler.HandleMessage(request);
    assertThat(responses, is(notNullValue()));
    assertThat(responses.size(), is(1));
    assertThat(responses.iterator().next().getMessage(), is("ok, pass is xyy"));
  }

  @Test
  public void testHandleMessageSearch() throws Exception {
    final HighriseHandler highriseHandler = new HighriseHandler();
    final Request request = new Request();
    request.setBot("MyBot");
    request.setMessage("/highrise search xyy");
    final Collection<Response> responses = highriseHandler.HandleMessage(request);
    assertThat(responses, is(notNullValue()));
    assertThat(responses.size(), is(1));
    // assertThat(responses.iterator().next().getMessage(), startsWith("problems connecting with highrise"));
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
  public void testHandleMessageReturnMessageSetPass() throws Exception {
    final HighriseHandler highriseHandler = new HighriseHandler();
    final Request request = new Request();
    request.setBot("MyBot");
    request.setMessage("/help");
    final Collection<Response> responses = highriseHandler.HandleMessage(request);
    assertThat(responses, is(notNullValue()));
    assertThat(responses.size(), is(1));
    final Response response = responses.iterator().next();
    assertThat(response, is(notNullValue()));
    assertThat(response.getMessage(), is("Highrise bot:\n/highrise user value\nhighrise pass value"));
  }


  @Test
  public void testRegisterHighriseFail() throws Exception {
    final HighriseHandler highriseHandler = new HighriseHandler();

    try {
      Credentials credentials = new Credentials();
      credentials.setApiKey("s");
      credentials.setUserName("a");
      highriseHandler.registerHighriseUser(credentials);
      fail();
    } catch (HighriseException e) {

    }

  }

}
