package de.benjaminborbe.bot.highrise.messagehandler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.algaworks.highrisehq.Highrise;
import com.algaworks.highrisehq.managers.PeopleManager;

import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.highrise.HighriseFactory;

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

    final SearchMessageHandler searchMessageHandler = new SearchMessageHandler(highriseFactory);
    final Request request = new Request();
    request.setAuthToken(token);
    request.setMessage("/highrise search Uwe");
    final String ret = searchMessageHandler.handleMessage(request);

    assertThat(ret, is("sorry, i found no results for Uwe"));

  }
}
