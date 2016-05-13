package de.benjaminborbe.bot.highrise;

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
import com.fasterxml.jackson.core.JsonProcessingException;

import de.benjaminborbe.bot.agent.MessageHandler;
import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.agent.Response;

public class HighriseHandler implements MessageHandler {

  private static final Logger logger = LoggerFactory.getLogger(HighriseHandler.class);

  private HashMap<String, ConversionState> userStates = new HashMap<>();

  private List<ConversionState> conversionStates = new LinkedList<>();

  private UserDataService userDataService;

  @Inject
  public HighriseHandler(UserDataService userDataService) {
    this.userDataService = userDataService;
    conversionStates.add(0, new ConversionStateSubdomain());

  }

  @Override
  public Collection<Response> HandleMessage(final Request request) {

    final Response response = new Response();

    String message = request.getMessage();

    if (message.equals("/help") || message.equals("/start")) {
      response.setMessage(
          "I am HighriseBot.\n Learn more about me at https://highrisebot.com/documentation\n\nYou need a Highrise account to work with me. Go here to get one if you don’t have any: https://signup.37signals.com/highrise/free/signup/new\n\nPlease use the following commands to start your registration:\n\n/auth register [username]\n/highrise subdomain [value]\n/highrise apitoken [value]\n\nAfter that you’ll be ready to start searching your Highrise data:\n/highrise search [name]");

    } else if (message.startsWith("/highrise subdomain")) {
      String user = message.substring(new String("/highrise subdomain ").length());

      try {
        userDataService.storeUserName(request.getAuthToken(), user);
        response.setMessage("Alright, Your Highrise Subdomain is now set to: " + user);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
        response.setMessage(
            "Sorry, but the storing process of your subdomain failed, unfortunately. I am not smart enough to know why, yet. " + user);
      }

    } else if (message.startsWith("/highrise apitoken")) {
      String pass = message.substring(new String("/highrise apitoken ").length());
      try {
        userDataService.storeToken(request.getAuthToken(), pass);
        response.setMessage("Noted. Your API token for Highrise is now set to: " + pass);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
        response.setMessage(
            "Ouch! Something went terribly wrong. Storing of your API token failed. Unfortunately I have no glue, why this is.");
      }

    } else if (message.startsWith("/highrise search ")) {
      searchForPeople(request, response);

    }
 else {
      return Collections.emptyList();
    }

    return Collections.singletonList(response);
  }

  private void searchForPeople(final Request request, final Response response) {
    String searchString = request.getMessage().substring(new String("/highrise search ").length());
    try {
      Credentials credentials = userDataService.getCredentials(request.getAuthToken());
      Highrise highrise = new Highrise(credentials.getUserName(), credentials.getApiKey());
      List<Person> persons = highrise.getPeopleManager().searchByCustomField("term", searchString);
      if (persons.size() == 0) {
        response.setMessage("sorry, i found no results for " + searchString);
      } else {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("I found this persons for you:");
        for (Person person : persons) {
          stringBuilder.append(person.getFirstName() + " " + person.getLastName());
          if (person.getContactData().getEmailAddresses().size() > 0) {
            stringBuilder.append("\n");
            stringBuilder.append(person.getContactData().getEmailAddresses().get(0).getAddress());
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
