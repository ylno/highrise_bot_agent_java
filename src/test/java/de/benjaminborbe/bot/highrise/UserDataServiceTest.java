package de.benjaminborbe.bot.highrise;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDataServiceTest {

  @Test
  @Ignore
  public void testStoreUserName() throws Exception {

    final Config config = new Config();
    config.setAuthAdress("localhost:6666");
    config.setAuthUser("auth");
    config.setAuthPassword("test123");

    final UserDataService userDataService = new UserDataService(config, new ObjectMapper());
    final String authToken = "dGVzdGVyOnNlY3JldA==";
    userDataService.storeUserName(authToken, "hansuser");
    userDataService.storeToken(authToken, "hanstoken");

    final Credentials hans = userDataService.getCredentials(authToken);
    assertThat(hans.getApiKey(), is("hanstoken"));
    assertThat(hans.getUserName(), is("hansuser"));

  }

  @Test(expected = UserNotFoundException.class)
  @Ignore
  public void testUserNotFoundExceptionWhenUserNotRegistered() throws Exception {

    final Config config = new Config();
    config.setAuthAdress("localhost:6666");
    config.setAuthUser("auth");
    config.setAuthPassword("test123");

    final UserDataService userDataService = new UserDataService(config, new ObjectMapper());
    final String authToken = "notexistingAuthToken";
    userDataService.storeUserName(authToken, "hansuser");

  }

}
