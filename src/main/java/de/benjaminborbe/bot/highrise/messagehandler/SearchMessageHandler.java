package de.benjaminborbe.bot.highrise.messagehandler;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algaworks.highrisehq.Highrise;
import com.algaworks.highrisehq.bean.EmailAddress;
import com.algaworks.highrisehq.bean.Person;
import com.algaworks.highrisehq.bean.PhoneNumber;
import com.algaworks.highrisehq.managers.PeopleManager;

import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.highrise.Credentials;
import de.benjaminborbe.bot.highrise.HighriseFactory;
import de.benjaminborbe.bot.highrise.UserDataService;
import de.benjaminborbe.bot.highrise.UserNotFoundException;

public class SearchMessageHandler extends MessageHandler {

  private static final Logger logger = LoggerFactory.getLogger(SearchMessageHandler.class);

  public static final int LIMIT_FOR_EXTENDED_OUTPUT = 3;

  public static final String SCHEME = "https";

  public static final String HIGHRISEHQDOMAIN = "highrisehq.com";

  public static final String PEOPLE_PATH = "/people/";

  private final HighriseFactory highriseFactory;

  private final UserDataService userDataService;

  @Inject
  public SearchMessageHandler(final HighriseFactory highriseFactory, final UserDataService userDataService) {
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
        return "sorry, I found no results for " + searchString;
      } else {

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("I found " + persons.size() + " contact(s) for you:").append("\n");
        final boolean extended = persons.size() > LIMIT_FOR_EXTENDED_OUTPUT ? false : true;
        for (final Person person : persons) {

          if (extended) {
            stringBuilder.append(createLongPersonResult(request, person));
          } else {
            stringBuilder.append(createShortPersonResult(person));
          }

          stringBuilder.append("\n");
        }
        return stringBuilder.toString();
      }
    } catch (final Exception e) {
      logger.debug("Exception {}", e);
      return "problems connecting with highrise " + e.toString();
    }

  }

  public String createShortPersonResult(final Person person) {
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(person.getFirstName())
        .append(" ")
        .append(person.getLastName())
        .append("\n");
    if (person.getContactData() != null && person.getContactData().getEmailAddresses() != null
        && person.getContactData().getEmailAddresses().size() > 0) {
      stringBuilder.append("  ")
          .append(person.getContactData().getEmailAddresses().get(0).getAddress())
          .append("\n");
    }
    return stringBuilder.toString();
  }

  public String createLongPersonResult(final Request request, final Person person) throws IOException, UserNotFoundException {

    final StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(person.getFirstName())
        .append(" ")
        .append(person.getLastName())
        .append("\n");

    if (person.getContactData() != null && person.getContactData().getEmailAddresses() != null
        && person.getContactData().getEmailAddresses().size() > 0) {
      stringBuilder.append("E-Mail: \n");
      for (final EmailAddress emailAddress : person.getContactData().getEmailAddresses()) {
        stringBuilder.append("  ").append(emailAddress.getAddress()).append("\n");
      }
    }
    if (person.getContactData().getPhoneNumbers().size() > 0) {
      stringBuilder.append("Phone: \n");
      for (final PhoneNumber phoneNumber : person.getContactData().getPhoneNumbers()) {
        stringBuilder.append("  ").append(phoneNumber.getNumber()).append("\n");
      }

    }
    if (person.getCompanyName() != null && !person.getCompanyName().isEmpty()) {
      stringBuilder.append("Company: ").append(person.getCompanyName()).append("\n");
    }

    final Credentials credentials = userDataService.getCredentials(request.getAuthToken());
    stringBuilder.append(createDeepLink(person, credentials)).append("\n");

    stringBuilder.append("------------------------------\n");
    return stringBuilder.toString();
  }

  public String createDeepLink(final Person person, final Credentials credentials) {
    final String userName = credentials.getUserName();
    final Long id = person.getId();
    return SCHEME + "://" + userName + "." + HIGHRISEHQDOMAIN + PEOPLE_PATH + id;
  }
}
