package de.benjaminborbe.bot.hello;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import com.algaworks.highrisehq.Highrise;
import com.algaworks.highrisehq.HighriseException;
import com.algaworks.highrisehq.bean.Person;

import de.benjaminborbe.bot.agent.MessageHandler;
import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.agent.Response;
import de.benjaminborbe.bot.highrise.Credentials;

public class HighriseHandler implements MessageHandler {

  private HashMap<String, ConversionState> userStates = new HashMap<>();

  private String userUrl;

  private String apiKey;

  private List<ConversionState> conversionStates = new LinkedList<>();

  @Inject
  public HighriseHandler() {
    conversionStates.add(0, new ConversionStateSubdomain());

    userUrl = "";
    apiKey = "";
  }

  @Override
  public Collection<Response> HandleMessage(final Request request) {

    // String authToken = request.getAuthToken();

    // if (!userStates.containsKey(authToken)) {
    // userStates.put(authToken, conversionStates.get(0));
    // }
    // ConversionState conversionState = userStates.get(authToken);
    final Response response = new Response();

    String message = request.getMessage();

    if (message.equals("/help")) {
      response.setMessage("Highrise bot:\n/highrise user value\nhighrise pass value");
    } else if (message.startsWith("/highrise user")) {
      userUrl = message.substring(new String("/highrise user ").length());
      response.setMessage("ok, user is " + userUrl);
    } else if (message.startsWith("/highrise pass")) {
      apiKey = message.substring(new String("/highrise pass ").length());
      response.setMessage("ok, pass is " + apiKey);
    } else if (message.startsWith("/highrise search ")) {
      String searchString = message.substring(new String("/highrise search ").length());
      Highrise highrise = new Highrise(userUrl, apiKey);
      try {
        List<Person> persons = highrise.getPeopleManager().searchByCustomField("term", searchString);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("result: ");
        for (Person person : persons) {
          stringBuilder.append(person.getFirstName() + " " + person.getLastName());
          if (person.getContactData().getEmailAddresses().size() > 0) {
            stringBuilder.append(person.getContactData().getEmailAddresses().get(0).getAddress());
          }
          response.setMessage(stringBuilder.toString());
        }
      } catch (HighriseException e) {
        response.setMessage("problems connecting with highrise " + e.toString());
      } catch (Exception e) {
        response.setMessage("problems connecting with highrise " + e.toString());
      }

    }

    else if (message.startsWith("/highrise pass")) {

    } else {
      return Collections.emptyList();
    }

    return Collections.singletonList(response);
  }

  public void registerHighriseUser(Credentials credentials) {
    Highrise highrise = new Highrise(credentials.getUserName(), credentials.getApiKey());
    List<Person> all = highrise.getPeopleManager().getAll(new Long(1));
  }
}
