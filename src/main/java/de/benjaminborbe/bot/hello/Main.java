package de.benjaminborbe.bot.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.benjaminborbe.bot.agent.Address;
import de.benjaminborbe.bot.agent.runner.Runner;
import de.benjaminborbe.bot.hello.guice.GuiceModule;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  private static final String botname = "java";

  public static void main(final String[] args) {
    try {
      logger.debug("bot started");
      final Address nsqdAddress = Address.parse(System.getenv("NSQD_ADDRESS"));
      final Address nsqLookupdAddress = Address.parse(System.getenv("NSQ_LOOKUPD_ADDRESS"));
      final Injector injector = Guice.createInjector(new GuiceModule());
      final Runner instance = injector.getInstance(Runner.class);
      System.out.println("starting bot");
      instance.run(nsqdAddress, nsqLookupdAddress, botname);
    } catch (final Exception e) {
      System.out.println("bot died");
      logger.warn("bot failed", e);
    }
  }
}
