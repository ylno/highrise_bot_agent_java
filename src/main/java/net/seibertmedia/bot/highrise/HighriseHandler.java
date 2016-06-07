package net.seibertmedia.bot.highrise;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import net.seibertmedia.bot.highrise.messagehandler.BotMessageHandler;
import net.seibertmedia.bot.highrise.messagehandler.WrongBotMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.benjaminborbe.bot.agent.MessageHandler;
import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.agent.Response;
import net.seibertmedia.bot.highrise.messagehandler.ApikeyBotMessageHandler;
import net.seibertmedia.bot.highrise.messagehandler.HelpBotMessageHandler;
import net.seibertmedia.bot.highrise.messagehandler.SearchBotMessageHandler;
import net.seibertmedia.bot.highrise.messagehandler.SubDomainBotMessageHandler;

public class HighriseHandler implements MessageHandler {

  private static final Logger logger = LoggerFactory.getLogger(HighriseHandler.class);

  private final List<BotMessageHandler> botMessageHandlers = new ArrayList<>();

  private final Config config;

  @Inject
  public HighriseHandler(final UserDataService userDataService, final HighriseFactory highriseFactory, final Config config) {
    this.config = config;
    botMessageHandlers.add(new HelpBotMessageHandler());
    botMessageHandlers.add(new SubDomainBotMessageHandler(userDataService));
    botMessageHandlers.add(new ApikeyBotMessageHandler(userDataService));
    botMessageHandlers.add(new SearchBotMessageHandler(highriseFactory, userDataService));
    botMessageHandlers.add(new WrongBotMessageHandler());

  }

  @Override
  public Collection<Response> HandleMessage(final Request request) {

    if(isDenied(request)) {
      logger.debug("access denied for authtoken {}", request.getAuthToken());
      return Collections.emptyList();
    }

    final Response response = new Response();
    for (final BotMessageHandler botMessageHandler : botMessageHandlers) {
      if (botMessageHandler.matches(request.getMessage())) {
        final String message = botMessageHandler.handleMessage(request);
        logger.debug(message);
        response.setMessage(message);
        return Collections.singletonList(response);
      }
    }
    return Collections.emptyList();
  }

  public boolean isDenied(final Request request) {

    if(config==null || config.getRestrictToTokens()==null || config.getRestrictToTokens().isEmpty()) {
      return false;
    }

    return  !config.getRestrictToTokens().equals(request.getAuthToken());
  }

}
