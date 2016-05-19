package de.benjaminborbe.bot.hello;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.Collection;

import org.junit.Ignore;
import org.junit.Test;

import com.algaworks.highrisehq.HighriseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.agent.Response;
import de.benjaminborbe.bot.highrise.Config;
import de.benjaminborbe.bot.highrise.Credentials;
import de.benjaminborbe.bot.highrise.HighriseHandler;
import de.benjaminborbe.bot.highrise.UserDataService;
import de.benjaminborbe.bot.highrise.messagehandler.SubDomainMessageHandler;

public class HighriseHandlerTest {

  @Test
  public void testHandleMessageReturnNotNullResult() throws Exception {
    final HighriseHandler highriseHandler = getHighriseHandler();
    final Request request = new Request();
    request.setBot("MyBot");
    request.setMessage("hello MyBot");
    final Collection<Response> responses = highriseHandler.HandleMessage(request);
    assertThat(responses, is(notNullValue()));
  }

  private HighriseHandler getHighriseHandler() {
    UserDataService userDataService = new UserDataService(new Config(), new ObjectMapper());
    return new HighriseHandler(userDataService, new SubDomainMessageHandler(userDataService));
  }

  @Ignore
  @Test
  public void testHandleMessageUser() throws Exception {
    UserDataService userDataService = mock(UserDataService.class);
    HighriseHandler highriseHandler = new HighriseHandler(userDataService, new SubDomainMessageHandler(userDataService));

    final Request request = new Request();
    request.setBot("MyBot");
    request.setMessage("/highrise subdomain xyz");
    final Collection<Response> responses = highriseHandler.HandleMessage(request);
    assertThat(responses, is(notNullValue()));
    assertThat(responses.size(), is(1));
    assertThat(responses.iterator().next().getMessage(), is("Alright, Your Highrise Subdomain is now set to: xyz"));
  }

  @Ignore
  @Test
  public void testHandleMessagePass() throws Exception {
    UserDataService userDataService = mock(UserDataService.class);

    final HighriseHandler highriseHandler = new HighriseHandler(userDataService, new SubDomainMessageHandler(userDataService));
    final Request request = new Request();
    request.setBot("MyBot");
    request.setMessage("/highrise apitoken xyy");
    final Collection<Response> responses = highriseHandler.HandleMessage(request);
    assertThat(responses, is(notNullValue()));
    assertThat(responses.size(), is(1));
    assertThat(responses.iterator().next().getMessage(), is("Noted. Your API token for Highrise is now set to: xyy"));
  }

  @Test
  public void testHandleMessageSearch() throws Exception {
    final HighriseHandler highriseHandler = getHighriseHandler();
    final Request request = new Request();
    request.setBot("MyBot");
    request.setMessage("/highrise search xyy");
    final Collection<Response> responses = highriseHandler.HandleMessage(request);
    assertThat(responses, is(notNullValue()));
    assertThat(responses.size(), is(1));
    assertThat(responses.iterator().next().getMessage(), startsWith("problems connecting with highrise"));
  }

  @Test
  public void testHandleMessageReturnNoMessageIfPatternNotMatches() throws Exception {
    final HighriseHandler highriseHandler = getHighriseHandler();
    final Request request = new Request();
    request.setBot("MyBot");
    request.setMessage("hello foo");
    final Collection<Response> responses = highriseHandler.HandleMessage(request);
    assertThat(responses, is(notNullValue()));
    assertThat(responses.size(), is(0));
  }

  @Ignore
  @Test
  public void testHandleMessageReturnMessageSetPass() throws Exception {
    final HighriseHandler highriseHandler = getHighriseHandler();
    final Request request = new Request();
    request.setBot("MyBot");
    request.setMessage("/help");
    final Collection<Response> responses = highriseHandler.HandleMessage(request);
    assertThat(responses, is(notNullValue()));
    assertThat(responses.size(), is(1));
    final Response response = responses.iterator().next();
    assertThat(response, is(notNullValue()));
    assertThat(response.getMessage(), startsWith("I am HighriseBot"));
  }

  @Test
  public void testRegisterHighriseFail() throws Exception {
    final HighriseHandler highriseHandler = getHighriseHandler();
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
