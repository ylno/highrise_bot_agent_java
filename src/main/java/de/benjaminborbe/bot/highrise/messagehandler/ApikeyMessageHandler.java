package de.benjaminborbe.bot.highrise.messagehandler;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.highrise.UserDataService;

public class ApikeyMessageHandler extends MessageHandler {

  private UserDataService userDataService;

  @Inject
  public ApikeyMessageHandler(UserDataService userDataService) {

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
    String pass = request.getMessage().substring(new String("/highrise apitoken ").length());
    try {
      userDataService.storeToken(request.getAuthToken(), pass);
      return "Noted. Your API token for Highrise is now set to: " + pass;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return "Ouch! Something went terribly wrong. Storing of your API token failed. Unfortunately I have no glue, why this is.";
    }
  }
}
