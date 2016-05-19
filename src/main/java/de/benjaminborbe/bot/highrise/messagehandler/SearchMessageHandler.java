package de.benjaminborbe.bot.highrise.messagehandler;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algaworks.highrisehq.Highrise;
import com.algaworks.highrisehq.HighriseException;
import com.algaworks.highrisehq.bean.Person;
import com.algaworks.highrisehq.bean.PhoneNumber;

import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.highrise.Credentials;
import de.benjaminborbe.bot.highrise.UserDataService;

public class SearchMessageHandler extends MessageHandler {

  private static final Logger logger = LoggerFactory.getLogger(SearchMessageHandler.class);

  private UserDataService userDataService;

  @Inject
  public SearchMessageHandler(UserDataService userDataService) {

    this.userDataService = userDataService;
  }

  @Override
  public boolean matches(final String request) {
    if (request.startsWith("/highrise search")) {
      return true;
    }
    return false;
  }

  @Override
  public String handleMessage(final Request request) {
    return searchForPeople(request);
  }

  private String searchForPeople(final Request request) {
    String searchString = request.getMessage().substring(new String("/highrise search ").length());
    logger.debug(searchString);
    try {
      Credentials credentials = userDataService.getCredentials(request.getAuthToken());
      Highrise highrise = new Highrise(credentials.getUserName(), credentials.getApiKey());
      List<Person> persons = highrise.getPeopleManager().searchByCustomField("term", searchString);
      if (persons.size() == 0) {
        return "sorry, i found no results for " + searchString;
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
          return stringBuilder.toString();
        }
      }
    } catch (HighriseException e) {
      return "problems connecting with highrise " + e.toString();
    } catch (Exception e) {
      return "problems connecting with highrise " + e.toString();
    }

    return "sorry, i found no results for " + searchString;
  }
}
