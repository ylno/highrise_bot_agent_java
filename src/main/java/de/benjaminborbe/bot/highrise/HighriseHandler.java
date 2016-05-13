package de.benjaminborbe.bot.highrise;

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

public class HighriseHandler implements MessageHandler {

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

    // String authToken = request.getAuthToken();

    // if (!userStates.containsKey(authToken)) {
    // userStates.put(authToken, conversionStates.get(0));
    // }
    // ConversionState conversionState = userStates.get(authToken);
    final Response response = new Response();

    String message = request.getMessage();

    if (message.equals("/help")) {
      response.setMessage("Highrise bot:\n/highrise user [value]\n/highrise pass [value]\n/highrise search [name]");
    } else if (message.startsWith("/highrise user")) {
      String user = message.substring(new String("/highrise user ").length());
      userDataService.storeUserName(user);
      response.setMessage("ok, user is " + user);
    } else if (message.startsWith("/highrise pass")) {
      String pass = message.substring(new String("/highrise pass ").length());
      userDataService.storeToken(pass);
      response.setMessage("ok, pass is " + pass);
    } else if (message.startsWith("/highrise search ")) {
      searchForPeople(request, response);

    }

    else if (message.startsWith("/highrise pass")) {

    } else {
      return Collections.emptyList();
    }

    return Collections.singletonList(response);
  }

  private void searchForPeople(final Request request, final Response response) {
    String searchString = request.getMessage().substring(new String("/highrise search ").length());
    Credentials credentials = userDataService.getCredentials(request.getAuthToken());
    Highrise highrise = new Highrise(credentials.getUserName(), credentials.getApiKey());
    try {
      List<Person> persons = highrise.getPeopleManager().searchByCustomField("term", searchString);
      if (persons.size() == 0) {
        response.setMessage("sorry, i found no results for " + searchString);
      } else {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("result: ");
        for (Person person : persons) {
          stringBuilder.append(person.getFirstName() + " " + person.getLastName());
          if (person.getContactData().getEmailAddresses().size() > 0) {
            stringBuilder.append(person.getContactData().getEmailAddresses().get(0).getAddress());
          }
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
