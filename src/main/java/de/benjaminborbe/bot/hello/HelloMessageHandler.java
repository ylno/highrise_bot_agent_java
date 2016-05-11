package de.benjaminborbe.bot.hello;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

import de.benjaminborbe.bot.agent.MessageHandler;
import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.agent.Response;

public class HelloMessageHandler implements MessageHandler {

  @Inject
  public HelloMessageHandler() {
  }

  @Override
  public Collection<Response> HandleMessage(final Request request) {
    if (request.getMessage().equals("hello " + request.getBot())) {
      final Response response = new Response();
      response.setMessage("hello");
      return Collections.singletonList(response);
    }
    return Collections.emptyList();
  }
}
