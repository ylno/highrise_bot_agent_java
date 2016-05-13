package de.benjaminborbe.bot.highrise;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algaworks.highrisehq.Highrise;
import com.algaworks.highrisehq.bean.Person;
import com.algaworks.highrisehq.bean.PhoneNumber;

public class ApiTest {

  private static final Logger logger = LoggerFactory.getLogger(ApiTest.class);

  @Test
  public void testAPI() throws Exception {

    Highrise highrise = getHighrise();
    List<Person> people = highrise.getPeopleManager().getAll(new Long(0));
    people.size();

    for (Person person : people) {
      logger.debug("Lastname " + person.getLastName());
    }
  }

  private Highrise getHighrise() {
    String user = System.getenv("highrise_user");
    String password = System.getenv("highrise_pass");
    assertThat(password, is(notNullValue()));

    return new Highrise(user, password);
  }

  @Test
  public void testAPISearch() throws Exception {
    Highrise highrise = getHighrise();
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


}
