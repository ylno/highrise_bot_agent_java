package de.benjaminborbe.bot.highrise.messagehandler;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algaworks.highrisehq.Highrise;
import com.algaworks.highrisehq.bean.Person;
import com.algaworks.highrisehq.bean.PhoneNumber;
import com.algaworks.highrisehq.managers.PeopleManager;

import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.highrise.Credentials;
import de.benjaminborbe.bot.highrise.HighriseFactory;
import de.benjaminborbe.bot.highrise.UserDataService;

public class SearchMessageHandler extends MessageHandler {

  private static final Logger logger = LoggerFactory.getLogger(SearchMessageHandler.class);

  public static final int LIMIT_FOR_EXTENDED_OUTPUT = 3;

  public static final String SCHEME = "https";

  public static final String HIGHRISEHQDOMAIN = "highrisehq.com";

  public static final String PEOPLE_PATH = "/people/";

  private HighriseFactory highriseFactory;

  private UserDataService userDataService;

  @Inject
  public SearchMessageHandler(HighriseFactory highriseFactory, UserDataService userDataService) {
    this.highriseFactory = highriseFactory;
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
    final String searchString = request.getMessage().substring(new String("/highrise search ").length());
    logger.debug(searchString);
    try {
      final Highrise highrise = highriseFactory.get(request.getAuthToken());
      final PeopleManager peopleManager = highrise.getPeopleManager();
      final List<Person> persons = peopleManager.searchByCustomField("term", searchString);
      if (persons == null || persons.size() == 0) {
        return "sorry, i found no results for " + searchString;
      } else {

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("I found " + persons.size() + " contact(s) for you:");
        boolean extended = persons.size() > LIMIT_FOR_EXTENDED_OUTPUT ? false : true;
        for (Person person : persons) {

          if (extended) {
            createLongPersonResult(request, stringBuilder, person);
          } else {

            createShortPersonResult(stringBuilder, person);
          }

          stringBuilder.append("\n");
          return stringBuilder.toString();
        }
      }
    } catch (Exception e) {
      logger.debug("Exception {}", e);
      return "problems connecting with highrise " + e.toString();
    }

    return "sorry, i found no results for " + searchString;
  }

  private void createShortPersonResult(final StringBuilder stringBuilder, final Person person) {
    stringBuilder.append(person.getFirstName() + " " + person.getLastName());
    if (person.getContactData() != null && person.getContactData().getEmailAddresses() != null
        && person.getContactData().getEmailAddresses().size() > 0) {
      stringBuilder.append("\n");
      stringBuilder.append(person.getContactData().getEmailAddresses().get(0).getAddress());
    }
    stringBuilder.append("\n------------------------------\n");
  }

  private void createLongPersonResult(final Request request, final StringBuilder stringBuilder, final Person person) throws IOException {
    stringBuilder.append(person.getFirstName() + " " + person.getLastName());
    if (person.getContactData() != null && person.getContactData().getEmailAddresses() != null
        && person.getContactData().getEmailAddresses().size() > 0) {
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

    Credentials credentials = userDataService.getCredentials(request.getAuthToken());
    stringBuilder.append(createDeepLink(person, credentials));

    stringBuilder.append("\n------------------------------\n");
  }

  public String createDeepLink(final Person person, final Credentials credentials) {
    return SCHEME + "://" + credentials.getUserName() + "." + HIGHRISEHQDOMAIN + PEOPLE_PATH + person.getId();
  }
}
