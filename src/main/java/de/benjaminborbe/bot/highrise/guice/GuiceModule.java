package de.benjaminborbe.bot.highrise.guice;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

import de.benjaminborbe.bot.agent.MessageHandler;
import de.benjaminborbe.bot.highrise.Config;
import de.benjaminborbe.bot.highrise.HighriseFactory;
import de.benjaminborbe.bot.highrise.HighriseHandler;

public class GuiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(MessageHandler.class).to(HighriseHandler.class);
    bind(Config.class).in(Singleton.class);
    bind(HighriseFactory.class);
  }
}
