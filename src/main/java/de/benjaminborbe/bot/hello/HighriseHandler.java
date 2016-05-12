package de.benjaminborbe.bot.hello;

import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

import de.benjaminborbe.bot.agent.MessageHandler;
import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.agent.Response;

public class HighriseHandler implements MessageHandler {

  @Inject
  public HighriseHandler() {
  }

  @Override
  public Collection<Response> HandleMessage(final Request request) {

    if (request.getMessage().equals("hello " + request.getBot())) {
      final Response response = new Response();
      if (request.getFrom() != null) {
        response.setMessage(String.format("hello %s from java", request.getFrom().getUsername()));
      } else {
        response.setMessage("hello from java");
      }
      return Collections.singletonList(response);
    }
    return Collections.emptyList();
  }
}
