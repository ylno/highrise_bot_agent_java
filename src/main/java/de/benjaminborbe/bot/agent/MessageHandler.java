package de.benjaminborbe.bot.agent;

import java.util.Collection;

public interface MessageHandler {

  Collection<Response> HandleMessage(Request request);

}
