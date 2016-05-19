package de.benjaminborbe.bot.highrise;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algaworks.highrisehq.Highrise;
import com.algaworks.highrisehq.bean.Person;
import com.algaworks.highrisehq.bean.PhoneNumber;

/* This test needs to be env-Vars highrise_user and highrise_pass set. If it does not find them it does nothing */

public class ApiTest {

  private static final Logger logger = LoggerFactory.getLogger(ApiTest.class);

  @Test
  public void testAPI() throws Exception {

    Highrise highrise = getHighrise();
    if (highrise == null) {
      return;
    }
    List<Person> people = highrise.getPeopleManager().getAll(new Long(0));
    people.size();

    for (Person person : people) {
      logger.debug("Lastname " + person.getLastName());
    }
  }

  @Test
  public void testAPISearch() throws Exception {
    Highrise highrise = getHighrise();
    if (highrise == null) {
      return;
    }
    List<Person> people = highrise.getPeopleManager().searchByCustomField("term", "martin");
    logger.debug("Size: " + people.size());

    for (Person person : people) {
      logger.debug("Firstname " + person.getFirstName());
      logger.debug("Lastname " + person.getLastName());
      logger.debug("Company " + person.getCompanyName());
      List<PhoneNumber> phoneNumbers = person.getContactData().getPhoneNumbers();

      for (PhoneNumber phoneNumber : phoneNumbers) {
        logger.debug("Phone " + phoneNumber.getNumber());
      }

      logger.debug("id " + person.getId());
    }
  }

  private Highrise getHighrise() {
    String user = System.getenv("highrise_user");
    String password = System.getenv("highrise_pass");

    if (user == null || user.isEmpty() || password == null || password.isEmpty()) {
      return null;
    }

    return new Highrise(user, password);
  }


}
