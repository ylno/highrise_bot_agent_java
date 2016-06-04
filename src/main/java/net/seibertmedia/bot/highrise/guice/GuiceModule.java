package net.seibertmedia.bot.highrise.guice;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

import de.benjaminborbe.bot.agent.MessageHandler;
import net.seibertmedia.bot.highrise.Config;
import net.seibertmedia.bot.highrise.HighriseFactory;
import net.seibertmedia.bot.highrise.HighriseHandler;

public class GuiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(MessageHandler.class).to(HighriseHandler.class);
    bind(Config.class).in(Singleton.class);
    bind(HighriseFactory.class);
  }
}
