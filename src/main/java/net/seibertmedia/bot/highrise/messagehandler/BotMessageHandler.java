package net.seibertmedia.bot.highrise.messagehandler;

import de.benjaminborbe.bot.agent.Request;

public abstract class BotMessageHandler {

  public abstract boolean matches(String request);

  public abstract String handleMessage(Request request);
}
