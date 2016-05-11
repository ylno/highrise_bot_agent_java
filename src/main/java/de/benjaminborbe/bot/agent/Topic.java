package de.benjaminborbe.bot.agent;

public enum Topic {

  REQUEST("bot-request"), RESPONSE("bot-response");

  private final String name;

  Topic(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
