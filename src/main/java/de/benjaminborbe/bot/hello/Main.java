package de.benjaminborbe.bot.hello;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.benjaminborbe.bot.agent.runner.Runner;
import de.benjaminborbe.bot.hello.guice.GuiceModule;

public class Main {

  public static void main(final String[] args) {
    final Injector injector = Guice.createInjector(new GuiceModule());
    final Runner instance = injector.getInstance(Runner.class);
    System.out.println("starting bot");
    try {
      instance.run();
    } catch (final Exception e) {
      System.out.println("bot died");
    }
  }
}
