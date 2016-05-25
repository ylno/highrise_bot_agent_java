package de.benjaminborbe.bot.highrise;

import java.io.IOException;
import java.util.Base64;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class UserDataService {

  private static final Logger logger = LoggerFactory.getLogger(UserDataService.class);

  public static final String HIGHRISE_SUBDOMAIN = "highrise_subdomain";

  public static final String HIGHRISE_APIKEY = "highrise_apikey";

  private final Config config;

  private final ObjectMapper objectMapper;


  @Inject
  public UserDataService(final Config config, final ObjectMapper objectMapper) {
    this.config = config;
    this.objectMapper = objectMapper;
  }

  public Credentials getCredentials(final String authToken) throws IOException, UserNotFoundException {

    final String internalUser = tokenToUsername(authToken);

    final Credentials credentials = new Credentials();

    credentials.setUserName(read(internalUser, HIGHRISE_SUBDOMAIN));
    credentials.setApiKey(read(internalUser, HIGHRISE_APIKEY));

    return credentials;
  }

  private String read(final String internalUser, final String field) throws IOException {
    final String authPassword = config.getAuthPassword();
    final String authUser = config.getAuthUser();

    final String apiToken = new String(Base64.getEncoder().encode(new String(authUser + ":" + authPassword).getBytes()));

    logger.debug(apiToken);

    final ClientConfig clientConfig = new DefaultClientConfig();
    final Client client = Client.create(clientConfig);

    final WebResource webResource = client
        .resource(UriBuilder.fromUri("http://" + config.getAuthAdress() + "/user/" + internalUser + "/data/" + field).build());

    final String response = webResource.header("Authorization", "Bearer " + apiToken).type(MediaType.APPLICATION_FORM_URLENCODED)
        .get(String.class);

    final String value = objectMapper.readValue(response, String.class);
    logger.debug("storage respone: {}", response);
    logger.debug("store username: {}", webResource.getURI());
    return value;
  }

  public void storeUserName(final String authToken, final String highriseSubDomain) throws IOException, UserNotFoundException {
    final String keyToSet = HIGHRISE_SUBDOMAIN;

    final String internalUser = tokenToUsername(authToken);

    storeValue(internalUser, keyToSet, highriseSubDomain);
  }

  public void storeToken(final String authToken, final String apiKey) throws IOException, UserNotFoundException {

    final String internalUser = tokenToUsername(authToken);
    storeValue(internalUser, HIGHRISE_APIKEY, apiKey);
  }

  private String tokenToUsername(final String authToken) throws IOException, UserNotFoundException {
    final String authPassword = config.getAuthPassword();
    final String authUser = config.getAuthUser();

    final String apiToken = new String(Base64.getEncoder().encode(new String(authUser + ":" + authPassword).getBytes()));

    logger.debug(apiToken);

    final ClientConfig clientConfig = new DefaultClientConfig();
    final Client client = Client.create(clientConfig);
    final WebResource webResource = client
        .resource(UriBuilder.fromUri("http://" + config.getAuthAdress() + "/login").build());

    final String s = "{\"authToken\": \"" + authToken + "\"}";
    final byte[] json = s.getBytes("UTF-8");

    final ClientResponse response = webResource.header("Authorization", "Bearer " + apiToken).type(MediaType.APPLICATION_FORM_URLENCODED)
        .post(ClientResponse.class, json);

    if (response.getStatus() == 404) {
      throw new UserNotFoundException();
    }

    final String output = response.getEntity(String.class);

    final UserData userData = objectMapper.readValue(output, UserData.class);

    logger.debug("storage respone tokenToUsername: {}", userData.getUser());

    logger.debug("store username: {}", webResource.getURI());

    return userData.getUser();
  }

  private void storeValue(final String internalUser, final String keyToSet, final String value) throws JsonProcessingException {
    final String authPassword = config.getAuthPassword();
    final String authUser = config.getAuthUser();

    final String apiToken = new String(Base64.getEncoder().encode(new String(authUser + ":" + authPassword).getBytes()));

    logger.debug(apiToken);

    final ClientConfig clientConfig = new DefaultClientConfig();
    final Client client = Client.create(clientConfig);
    final WebResource webResource = client
        .resource(UriBuilder.fromUri("http://" + config.getAuthAdress() + "/user/" + internalUser + "/data/" + keyToSet).build());

    final byte[] json;
    json = objectMapper.writeValueAsBytes(value);

    final ClientResponse response = webResource.header("Authorization", "Bearer " + apiToken).type(MediaType.APPLICATION_FORM_URLENCODED)
        .post(ClientResponse.class, json);


    logger.debug("storage respone: {}", response);

    logger.debug("store username: {}", webResource.getURI());
  }

}

class UserData {

  public String getUser() {
    return user;
  }

  public void setUser(final String user) {
    this.user = user;
  }

  private String user;
}
