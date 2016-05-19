package de.benjaminborbe.bot.highrise;

import java.io.IOException;

import javax.inject.Inject;

import com.algaworks.highrisehq.Highrise;

public class HighriseFactory {

  private UserDataService userDataService;

  @Inject
  public HighriseFactory(UserDataService userDataService) {
    this.userDataService = userDataService;
  }

  public Highrise get(String authToken) throws IOException {
    Credentials credentials = userDataService.getCredentials(authToken);
    Highrise highrise = new Highrise(credentials.getUserName(), credentials.getApiKey());
    return highrise;
  }
}
