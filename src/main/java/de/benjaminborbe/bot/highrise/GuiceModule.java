package de.benjaminborbe.bot.highrise;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

import de.benjaminborbe.bot.agent.MessageHandler;

public class GuiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(MessageHandler.class).to(HighriseHandler.class);
    bind(Config.class).in(Singleton.class);
    bind(HighriseFactory.class);
  }
}
