package de.benjaminborbe.bot.highrise;

import java.io.IOException;

import javax.inject.Inject;

import com.algaworks.highrisehq.Highrise;

public class HighriseFactory {

  private final UserDataService userDataService;

  @Inject
  public HighriseFactory(final UserDataService userDataService) {
    this.userDataService = userDataService;
  }

  public Highrise get(final String authToken) throws IOException {
    final Credentials credentials = userDataService.getCredentials(authToken);
    final Highrise highrise = new Highrise(credentials.getUserName(), credentials.getApiKey());
    return highrise;
  }
}
