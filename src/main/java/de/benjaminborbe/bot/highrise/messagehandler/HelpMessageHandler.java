package de.benjaminborbe.bot.highrise.messagehandler;

import de.benjaminborbe.bot.agent.Request;

public class HelpMessageHandler extends MessageHandler {

  @Override
  public boolean matches(String request) {
    if (request.equals("/help") || request.equals("/start")) {
      return true;
    }
    return false;
  }

  @Override
  public String handleMessage(Request request) {
    return "I am HighriseBot.\n Learn more about me at https://highrisebot.com/documentation\n\nYou need a Highrise account to work with me. Go here to get one if you don’t have any: https://signup.37signals.com/highrise/free/signup/new\n\nPlease use the following commands to start your registration:\n\n/auth register [username]\n/highrise subdomain [value]\n/highrise apitoken [value]\n\nAfter that you’ll be ready to start searching your Highrise data:\n/highrise search [name]";
  }

}
