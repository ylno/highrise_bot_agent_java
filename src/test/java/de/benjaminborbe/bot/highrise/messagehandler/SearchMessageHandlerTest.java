package de.benjaminborbe.bot.highrise.messagehandler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.algaworks.highrisehq.Highrise;
import com.algaworks.highrisehq.bean.Person;
import com.algaworks.highrisehq.managers.PeopleManager;

import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.highrise.Credentials;
import de.benjaminborbe.bot.highrise.HighriseFactory;
import de.benjaminborbe.bot.highrise.UserDataService;

public class SearchMessageHandlerTest {

  @Test
  public void testHandleMessage() throws Exception {
    final Highrise highrise = mock(Highrise.class);
    final PeopleManager peopleManager = mock(PeopleManager.class);
    when(highrise.getPeopleManager()).thenReturn(peopleManager);
    when(peopleManager.searchByCustomField("term", "Uwe")).thenReturn(null);

    final String token = "secret";
    final HighriseFactory highriseFactory = mock(HighriseFactory.class);
    when(highriseFactory.get(token)).thenReturn(highrise);

    final UserDataService userDataService = mock(UserDataService.class);

    final SearchMessageHandler searchMessageHandler = new SearchMessageHandler(highriseFactory, userDataService);
    final Request request = new Request();
    request.setAuthToken(token);
    request.setMessage("/highrise search Uwe");
    final String ret = searchMessageHandler.handleMessage(request);

    assertThat(ret, is("sorry, i found no results for Uwe"));

  }

  @Test
  public void testHandleLongList() throws Exception {

    final List<Person> searchResult = new ArrayList<>();
    final Person firstPerson = mock(Person.class);
    when(firstPerson.getId()).thenReturn(new Long(123));
    searchResult.add(firstPerson);
    searchResult.add(new Person());
    searchResult.add(new Person());
    searchResult.add(new Person());
    searchResult.add(new Person());

    final Highrise highrise = mock(Highrise.class);
    final PeopleManager peopleManager = mock(PeopleManager.class);
    when(highrise.getPeopleManager()).thenReturn(peopleManager);
    when(peopleManager.searchByCustomField("term", "Uwe")).thenReturn(searchResult);

    final String token = "secret";
    final HighriseFactory highriseFactory = mock(HighriseFactory.class);
    when(highriseFactory.get(token)).thenReturn(highrise);

    final UserDataService userDataService = mock(UserDataService.class);

    final SearchMessageHandler searchMessageHandler = new SearchMessageHandler(highriseFactory, userDataService);
    final Request request = new Request();
    request.setAuthToken(token);
    request.setMessage("/highrise search Uwe");
    final String ret = searchMessageHandler.handleMessage(request);

    assertThat(ret.startsWith("I found 5 contact"), is(true));

  }

  @Test
  public void testCreateDeepLink() throws Exception {

    final List<Person> searchResult = new ArrayList<>();
    final Person firstPerson = mock(Person.class);
    when(firstPerson.getId()).thenReturn(new Long(123));

    final HighriseFactory highriseFactory = mock(HighriseFactory.class);

    final UserDataService userDataService = mock(UserDataService.class);

    final SearchMessageHandler searchMessageHandler = new SearchMessageHandler(highriseFactory, userDataService);
    final Request request = new Request();
    final Credentials credentials = mock(Credentials.class);
    when(credentials.getUserName()).thenReturn("subdomainx");
    final String ret = searchMessageHandler.createDeepLink(firstPerson, credentials);

    assertThat(ret, is("https://subdomainx.highrisehq.com/people/123"));

  }
}