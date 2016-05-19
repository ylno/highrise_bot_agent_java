package de.benjaminborbe.bot.highrise.messagehandler;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.highrise.UserDataService;

public class SubDomainMessageHandler extends MessageHandler {

  private UserDataService userDataService;

  @Inject
  public SubDomainMessageHandler(UserDataService userDataService) {

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
    String user = request.getMessage().substring(new String("/highrise subdomain ").length());

    try {
      userDataService.storeUserName(request.getAuthToken(), user);
      return "Alright, Your Highrise Subdomain is now set to: " + user;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return "Sorry, but the storing process of your subdomain failed, unfortunately. I am not smart enough to know why, yet. " + user;
    }
  }
}
