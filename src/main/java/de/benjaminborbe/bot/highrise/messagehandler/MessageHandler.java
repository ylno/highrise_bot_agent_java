package de.benjaminborbe.bot.highrise.messagehandler;

public abstract class MessageHandler {

  public abstract boolean matches(String request);

  public abstract String handleMessage(String request);
}
