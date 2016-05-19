package de.benjaminborbe.bot.highrise.messagehandler;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algaworks.highrisehq.Highrise;
import com.algaworks.highrisehq.bean.Person;
import com.algaworks.highrisehq.bean.PhoneNumber;
import com.algaworks.highrisehq.managers.PeopleManager;

import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.highrise.HighriseFactory;

public class SearchMessageHandler extends MessageHandler {

  private static final Logger logger = LoggerFactory.getLogger(SearchMessageHandler.class);

  private final HighriseFactory highriseFactory;

  @Inject
  public SearchMessageHandler(final HighriseFactory highriseFactory) {
    this.highriseFactory = highriseFactory;
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
        final boolean extended = persons.size() > 3 ? false : true;
        for (final Person person : persons) {

          if (extended) {
            stringBuilder.append(person.getFirstName() + " " + person.getLastName());
            if (person.getContactData().getEmailAddresses().size() > 0) {
              stringBuilder.append("\nE-Mail:");
              stringBuilder.append(person.getContactData().getEmailAddresses().get(0).getAddress());
            }
            if (person.getContactData().getPhoneNumbers().size() > 0) {
              for (final PhoneNumber phoneNumber : person.getContactData().getPhoneNumbers()) {
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
    } catch (final Exception e) {
      logger.debug("Exception", e);
      return "problems connecting with highrise";
    }

    return "sorry, i found no results for " + searchString;
  }
}
