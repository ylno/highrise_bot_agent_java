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

import com.algaworks.highrisehq.Highrise;
import com.algaworks.highrisehq.HighriseException;
import com.algaworks.highrisehq.bean.Person;
import com.algaworks.highrisehq.bean.PhoneNumber;

import de.benjaminborbe.bot.agent.MessageHandler;
import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.agent.Response;
import de.benjaminborbe.bot.highrise.messagehandler.ApikeyMessageHandler;
import de.benjaminborbe.bot.highrise.messagehandler.HelpMessageHandler;
import de.benjaminborbe.bot.highrise.messagehandler.SearchMessageHandler;
import de.benjaminborbe.bot.highrise.messagehandler.SubDomainMessageHandler;

public class HighriseHandler implements MessageHandler {

  private static final Logger logger = LoggerFactory.getLogger(HighriseHandler.class);

  private HashMap<String, ConversionState> userStates = new HashMap<>();

  private List<ConversionState> conversionStates = new LinkedList<>();

  private List<de.benjaminborbe.bot.highrise.messagehandler.MessageHandler> messageHandlers = new ArrayList<>();

  private UserDataService userDataService;

  @Inject
  public HighriseHandler(UserDataService userDataService, SubDomainMessageHandler subDomainMessageHandler) {
    this.userDataService = userDataService;
    conversionStates.add(0, new ConversionStateSubdomain());

    messageHandlers.add(new HelpMessageHandler());
    messageHandlers.add(new SubDomainMessageHandler(userDataService));
    messageHandlers.add(new ApikeyMessageHandler(userDataService));
    messageHandlers.add(new SearchMessageHandler(userDataService));

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

  private void searchForPeople(final Request request, final Response response) {
    String searchString = request.getMessage().substring(new String("/highrise search ").length());
    logger.debug(searchString);
    try {
      Credentials credentials = userDataService.getCredentials(request.getAuthToken());
      Highrise highrise = new Highrise(credentials.getUserName(), credentials.getApiKey());
      List<Person> persons = highrise.getPeopleManager().searchByCustomField("term", searchString);
      if (persons.size() == 0) {
        response.setMessage("sorry, i found no results for " + searchString);
      } else {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("I found " + persons.size() + " contact(s) for you:");
        boolean extended = persons.size() > 3 ? false : true;
        for (Person person : persons) {

          if (extended) {
            stringBuilder.append(person.getFirstName() + " " + person.getLastName());
            if (person.getContactData().getEmailAddresses().size() > 0) {
              stringBuilder.append("\nE-Mail:");
              stringBuilder.append(person.getContactData().getEmailAddresses().get(0).getAddress());
            }
            if (person.getContactData().getPhoneNumbers().size() > 0) {
              for (PhoneNumber phoneNumber : person.getContactData().getPhoneNumbers()) {
                stringBuilder.append("\nPhone: " + phoneNumber.getNumber());
              }

            }
            if (!person.getCompanyName().isEmpty()) {
              stringBuilder.append("\nCompany: " + person.getCompanyName());
            }

            stringBuilder.append("\n------------------------------\n");
          } else {

            stringBuilder.append(person.getFirstName() + " " + person.getLastName());
            if (person.getContactData().getEmailAddresses().size() > 0) {
              stringBuilder.append("\n");
              stringBuilder.append(person.getContactData().getEmailAddresses().get(0).getAddress());
            }
            stringBuilder.append("\n------------------------------\n");
          }

          stringBuilder.append("\n");
          response.setMessage(stringBuilder.toString());
        }
      }
    } catch (HighriseException e) {
      response.setMessage("problems connecting with highrise " + e.toString());
    } catch (Exception e) {
      response.setMessage("problems connecting with highrise " + e.toString());
    }
  }

  public void registerHighriseUser(Credentials credentials) {
    Highrise highrise = new Highrise(credentials.getUserName(), credentials.getApiKey());
    List<Person> all = highrise.getPeopleManager().getAll(new Long(1));
  }
}
