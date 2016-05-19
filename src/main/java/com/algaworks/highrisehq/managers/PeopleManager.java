package com.algaworks.highrisehq.managers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algaworks.highrisehq.Highrise;
import com.algaworks.highrisehq.bean.People;
import com.algaworks.highrisehq.bean.Person;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * 
 * @author thiagofa
 *
 */
public class PeopleManager extends HighriseManager {

  private final static DateFormat yyyymmddhhMMss = new SimpleDateFormat("yyyyMMddHHmmss");

  private static final Logger logger = LoggerFactory.getLogger(PeopleManager.class);

  static {
    yyyymmddhhMMss.setTimeZone(new SimpleTimeZone(0, "UTC"));
  }

  public PeopleManager(final WebResource webResource, final String authorization) {
    super(webResource, authorization);
  }

  public Person get(final Long id) {
    return this.show(Person.class, Highrise.PEOPLE_UPDATE_PATH.replaceAll("#\\{id\\}", id.toString()));
  }

  public List<Person> getAll(final Long offset) {
    final MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add("n", offset.toString());
    return this.getAsList(Person.class, People.class, Highrise.PEOPLE_PATH, params);
  }

  public List<Person> since(final Date date, final Long offset) {
    final MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add("since", yyyymmddhhMMss.format(date));
    params.add("n", offset.toString());
    return this.getAsList(Person.class, People.class, Highrise.PEOPLE_PATH, params);

  }

  public List<Person> searchByCriteria(final String city, final String state, final String country, final String zip, final String phone,
      final String email) {
    final MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    if (city != null && !city.trim().equals("")) {
      params.add("criteria[city]", city);
    }
    if (state != null && !state.trim().equals("")) {
      params.add("criteria[state]", state);
    }
    if (country != null && !country.trim().equals("")) {
      params.add("criteria[country]", country);
    }
    if (zip != null && !zip.trim().equals("")) {
      params.add("criteria[zip]", zip);
    }
    if (phone != null && !phone.trim().equals("")) {
      params.add("criteria[phone]", phone);
    }
    if (email != null && !email.trim().equals("")) {
      params.add("criteria[email]", email);
    }

    return this.getAsList(Person.class, People.class, Highrise.PEOPLE_SEARCH_PATH, params);
  }

  public List<Person> searchByCustomField(final String customField, final String value) {
    final MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    if (customField != null && customField.equals("term")) {
      params.add(customField, value);
    } else if (customField != null && !customField.trim().equals("") && value != null) {
      params.add("criteria[" + customField + "]", value);
    }

    return this.getAsList(Person.class, People.class, Highrise.PEOPLE_SEARCH_PATH, params);
  }

  public Person create(final Person person) {
    return this.create(person, Highrise.PEOPLE_PATH);
  }

  public void update(final Person person) {
    this.update(person, Highrise.PEOPLE_UPDATE_PATH.replaceAll("#\\{id\\}", person.getId().toString()));
  }

}
