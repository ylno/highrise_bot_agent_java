package net.seibertmedia.bot.highrise;

import net.seibertmedia.bot.highrise.guice.GuiceModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.benjaminborbe.bot.agent.Address;
import de.benjaminborbe.bot.agent.Runner;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static final String AUTH_APPLICATION_PASSWORD = "AUTH_APPLICATION_PASSWORD";

  public static final String AUTH_APPLICATION_NAME = "AUTH_APPLICATION_NAME";

  public static final String AUTH_ADDRESS = "AUTH_ADDRESS";

  public static final String BOT_NAME = "BOT_NAME";

  public static final String ALLOWED_TOKEN = "ALLOWED_TOKEN";

  public static void main(final String[] args) {
    try {
      logger.debug("bot started");
      final Address nsqdAddress = Address.fromEnv("NSQD_ADDRESS");
      final Address nsqLookupdAddress = Address.fromEnv("NSQ_LOOKUPD_ADDRESS");

      final Injector injector = Guice.createInjector(new GuiceModule());
      final Config config = injector.getInstance(Config.class);

      final String auth_application_name = System.getenv(AUTH_APPLICATION_NAME);
      if (auth_application_name == null || auth_application_name.isEmpty()) {
        System.out.println("param " + AUTH_APPLICATION_NAME + " is missing");
        System.exit(1);
      }

      final String auth_application_password = System.getenv(AUTH_APPLICATION_PASSWORD);
      if (auth_application_password == null || auth_application_password.isEmpty()) {
        System.out.println("param " + AUTH_APPLICATION_PASSWORD + " is missing");
        System.exit(1);
      }

      final String auth_address = System.getenv(AUTH_ADDRESS);
      if (auth_address == null || auth_address.isEmpty()) {
        System.out.println("param " + AUTH_ADDRESS + " is missing");
        System.exit(1);
      }

      final String bot_name = System.getenv(BOT_NAME);
      if (bot_name == null || bot_name.isEmpty()) {
        System.out.println("param " + BOT_NAME + " is missing");
        System.exit(1);
      }

      final String allowed_token = System.getenv(ALLOWED_TOKEN);
      if (allowed_token != null && !allowed_token.isEmpty()) {
        System.out.println("param " + ALLOWED_TOKEN );
        config.setAllowedToken(allowed_token);

      }


      config.setAuthPassword(auth_application_password);
      config.setAuthUser(auth_application_name);
      config.setAuthAdress(auth_address);

      final Runner instance = injector.getInstance(Runner.class);

      System.out.println("starting bot");
      instance.run(nsqdAddress, nsqLookupdAddress, bot_name);
    } catch (final Exception e) {
      System.out.println("bot died");
      logger.warn("bot failed", e);
    }
  }
}
