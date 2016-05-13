package de.benjaminborbe.bot.highrise;

import javax.inject.Inject;

public class UserDataService {

  private Config config;

  private String userName;

  private String token;

  @Inject
  public UserDataService(Config config) {
    this.config = config;
  }

  public Credentials getCredentials(String AuthToken) {
    Credentials credentials = new Credentials();
    credentials.setUserName(userName);
    credentials.setApiKey(token);

    return credentials;
  }

  public void storeUserName(String userName) {

    this.userName = userName;
  }

  public void storeToken(String token) {

    this.token = token;
  }
}
