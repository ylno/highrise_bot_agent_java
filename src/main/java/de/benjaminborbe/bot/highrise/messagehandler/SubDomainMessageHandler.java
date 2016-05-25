package de.benjaminborbe.bot.highrise.messagehandler;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.highrise.UserDataService;
import de.benjaminborbe.bot.highrise.UserNotFoundException;

public class SubDomainMessageHandler extends MessageHandler {

  private static final Logger logger = LoggerFactory.getLogger(SubDomainMessageHandler.class);

  private final UserDataService userDataService;

  @Inject
  public SubDomainMessageHandler(final UserDataService userDataService) {

    this.userDataService = userDataService;
  }

  @Override
  public boolean matches(final String request) {
    if (request.startsWith("/highrise subdomain")) {
      return true;
    }
    return false;
  }

  @Override
  public String handleMessage(final Request request) {
    final String user = request.getMessage().substring(new String("/highrise subdomain ").length());

    try {
      userDataService.storeUserName(request.getAuthToken(), user);
      return "Alright, Your Highrise Subdomain is now set to: " + user;
    } catch (final java.io.IOException e) {
      logger.debug("storeUserName failed", e);
      return "Sorry, but the storing process of your subdomain failed, unfortunately. I am not smart enough to know why, yet. " + user;
    } catch (final UserNotFoundException e) {
      return "Your account is not registered please set a username first using /auth register [username]";
    }
  }
}
