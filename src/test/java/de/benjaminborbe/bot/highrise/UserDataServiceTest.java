package de.benjaminborbe.bot.highrise;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDataServiceTest {

  @Ignore
  @Test
  public void testStoreUserName() throws Exception {

    final Config config = new Config();
    config.setAuthAdress("localhost:8080");
    config.setAuthUser("auth");
    config.setAuthPassword("test123");

    final UserDataService userDataService = new UserDataService(config, new ObjectMapper());
    userDataService.storeUserName("dGVsZWdyYW06NzYxMzcyODE=", "hansuser");
    userDataService.storeToken("dGVsZWdyYW06NzYxMzcyODE=", "hanstoken");

    final Credentials hans = userDataService.getCredentials("dGVsZWdyYW06NzYxMzcyODE=");
    assertThat(hans.getApiKey(), is("hanstoken"));
    assertThat(hans.getUserName(), is("hansuser"));

  }

}
