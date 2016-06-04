package net.seibertmedia.bot.highrise.messagehandler;

import de.benjaminborbe.bot.agent.Request;

public class WrongBotMessageHandler extends BotMessageHandler {

  @Override
  public boolean matches(final String request) {
    if (request.startsWith("/highrise")) {
      return true;
    }
    return false;
  }

  @Override
  public String handleMessage(final Request request) {
    return "Sorry, I was unable to understand your message.\n Learn more about me at https://highrisebot.com/documentation\n\nYou need a Highrise account to work with me. Go here to get one if you don’t have any: https://signup.37signals.com/highrise/free/signup/new\n\nPlease use the following commands to start your registration:\n\n/auth register [username]\n/highrise subdomain [value]\n/highrise apitoken [value]\n\nAfter that you’ll be ready to start searching your Highrise data:\n/highrise search [name]";
  }

}
