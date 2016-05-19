package de.benjaminborbe.bot.highrise;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.benjaminborbe.bot.agent.MessageHandler;
import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.agent.Response;
import de.benjaminborbe.bot.highrise.messagehandler.ApikeyMessageHandler;
import de.benjaminborbe.bot.highrise.messagehandler.HelpMessageHandler;
import de.benjaminborbe.bot.highrise.messagehandler.SearchMessageHandler;
import de.benjaminborbe.bot.highrise.messagehandler.SubDomainMessageHandler;
import de.benjaminborbe.bot.highrise.messagehandler.WrongMessageHandler;

public class HighriseHandler implements MessageHandler {

  private static final Logger logger = LoggerFactory.getLogger(HighriseHandler.class);

  private HashMap<String, ConversionState> userStates = new HashMap<>();

  private List<ConversionState> conversionStates = new LinkedList<>();

  private List<de.benjaminborbe.bot.highrise.messagehandler.MessageHandler> messageHandlers = new ArrayList<>();

  private UserDataService userDataService;

  @Inject
  public HighriseHandler(UserDataService userDataService, final HighriseFactory highriseFactory) {
    this.userDataService = userDataService;
    conversionStates.add(0, new ConversionStateSubdomain());

    messageHandlers.add(new HelpMessageHandler());
    messageHandlers.add(new SubDomainMessageHandler(userDataService));
    messageHandlers.add(new ApikeyMessageHandler(userDataService));
    messageHandlers.add(new SearchMessageHandler(highriseFactory));
    messageHandlers.add(new HelpMessageHandler());
    messageHandlers.add(new WrongMessageHandler());

  }

  @Override
  public Collection<Response> HandleMessage(final Request request) {

    final Response response = new Response();


    for (de.benjaminborbe.bot.highrise.messagehandler.MessageHandler messageHandler : messageHandlers) {
      if (messageHandler.matches(request.getMessage())) {
        String message = messageHandler.handleMessage(request);
        logger.debug(message);
        response.setMessage(message);
        return Collections.singletonList(response);
      }
    }


    return Collections.emptyList();
  }

}
