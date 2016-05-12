package de.benjaminborbe.bot.hello;

import com.google.inject.AbstractModule;
import de.benjaminborbe.bot.agent.MessageHandler;

public class GuiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(MessageHandler.class).to(HighriseHandler.class);
  }
}
