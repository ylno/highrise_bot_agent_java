package net.seibertmedia.bot.highrise.messagehandler;

import javax.inject.Inject;

import net.seibertmedia.bot.highrise.UserDataService;
import net.seibertmedia.bot.highrise.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.benjaminborbe.bot.agent.Request;

public class ApikeyBotMessageHandler extends BotMessageHandler {

  private static final Logger logger = LoggerFactory.getLogger(ApikeyBotMessageHandler.class);

  private final UserDataService userDataService;

  @Inject
  public ApikeyBotMessageHandler(final UserDataService userDataService) {

    this.userDataService = userDataService;
  }

  @Override
  public boolean matches(final String request) {
    if (request.startsWith("/highrise apitoken")) {
      return true;
    }
    return false;
  }

  @Override
  public String handleMessage(final Request request) {
    final String pass = request.getMessage().substring(new String("/highrise apitoken ").length());
    try {
      userDataService.storeToken(request.getAuthToken(), pass);
      return "Noted. Your API token for Highrise is now set to: " + pass;
    } catch (final java.io.IOException e) {
      logger.debug("storeToken failed", e);
      return "Ouch! Something went terribly wrong. Storing of your API token failed. Unfortunately I have no glue, why this is.";
    } catch (final UserNotFoundException e) {
      return "Your account is not registered please set a username first using /auth register [username]";
    }
  }
}
