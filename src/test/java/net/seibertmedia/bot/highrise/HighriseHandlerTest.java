package net.seibertmedia.bot.highrise;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Collection;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.agent.Response;

public class HighriseHandlerTest {

  private HighriseFactory highriseFactory;

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
    Config config = new Config();
    final UserDataService userDataService = new UserDataService(config, new ObjectMapper());
    return new HighriseHandler(userDataService, highriseFactory, config);
  }

  @Test
  public void testHandleMessageUser() throws Exception {
    final UserDataService userDataService = mock(UserDataService.class);
    final HighriseHandler highriseHandler = new HighriseHandler(userDataService, highriseFactory, new Config());

    final Request request = new Request();
    request.setBot("MyBot");
    request.setMessage("/highrise subdomain xyz");
    final Collection<Response> responses = highriseHandler.HandleMessage(request);
    assertThat(responses, is(notNullValue()));
    assertThat(responses.size(), is(1));
    assertThat(responses.iterator().next().getMessage(), is("Alright, Your Highrise Subdomain is now set to: xyz"));
  }

  @Test
  public void testHandleMessagePass() throws Exception {
    final UserDataService userDataService = mock(UserDataService.class);

    final HighriseHandler highriseHandler = new HighriseHandler(userDataService, highriseFactory, new Config());
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
  public void testHandleWrongMessage() throws Exception {
    final HighriseHandler highriseHandler = getHighriseHandler();
    final Request request = new Request();
    request.setBot("MyBot");
    request.setMessage("/highrise illegal");
    final Collection<Response> responses = highriseHandler.HandleMessage(request);
    assertThat(responses, is(notNullValue()));
    assertThat(responses.size(), is(1));
    assertThat(responses.iterator().next().getMessage(), startsWith("Sorry, I was unable"));
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
  public void testIsDeniedFalse() throws Exception {
    final HighriseHandler highriseHandler = getHighriseHandler();
    final Request request = new Request();
    assertThat(false, is(highriseHandler.isDenied(request)));
  }

  @Test
  public void testIsDeniedEmptyToken() throws Exception {
    Config config = new Config();
    final UserDataService userDataService = new UserDataService(config, new ObjectMapper());
    final HighriseHandler highriseHandler = new HighriseHandler(userDataService, highriseFactory, config);
    config.setRestrictToTokens("xyz");
    final Request request = new Request();
    request.setAuthToken("xyz");
    assertThat(false, is(highriseHandler.isDenied(request)));
  }



  @Test
  public void testIsDeniedTokenFail() throws Exception {
    Config config = new Config();
    final UserDataService userDataService = new UserDataService(config, new ObjectMapper());
    final HighriseHandler highriseHandler = new HighriseHandler(userDataService, highriseFactory, config);
    config.setRestrictToTokens("xyza");
    final Request request = new Request();
    request.setAuthToken("xyz");
    assertThat(highriseHandler.isDenied(request), is(true));
  }

}
