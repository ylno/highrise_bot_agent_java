package de.benjaminborbe.bot.highrise;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDataServiceTest {

  @Test
  public void testStoreUserName() throws Exception {

    Config config = new Config();
    config.setAuthAdress("localhost:8080");
    config.setAuthUser("auth");
    config.setAuthPassword("test123");

    UserDataService userDataService = new UserDataService(config, new ObjectMapper());
    userDataService.storeUserName("hans", "hansuser");
    userDataService.storeToken("hans", "hanstoken");

    Credentials hans = userDataService.getCredentials("hans");
    assertThat(hans.getApiKey(), is("hanstoken"));
    assertThat(hans.getUserName(), is("hansuser"));

  }

}
