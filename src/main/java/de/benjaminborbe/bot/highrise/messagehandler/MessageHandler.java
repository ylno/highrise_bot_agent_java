package de.benjaminborbe.bot.highrise.messagehandler;

import de.benjaminborbe.bot.agent.Request;

public abstract class MessageHandler {

  public abstract boolean matches(String request);

  public abstract String handleMessage(Request request);
}
