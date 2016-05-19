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

  private String userName;

  private String token;

  @Inject
  public UserDataService(final Config config, final ObjectMapper objectMapper) {
    this.config = config;
    this.objectMapper = objectMapper;
  }

  public Credentials getCredentials(final String internalUser) throws IOException {
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

  public void storeUserName(final String internalUser, final String highriseSubDomain) throws JsonProcessingException {
    final String keyToSet = HIGHRISE_SUBDOMAIN;
    storeValue(internalUser, keyToSet, highriseSubDomain);
  }

  public void storeToken(final String internalUser, final String apiKey) throws JsonProcessingException {

    storeValue(internalUser, HIGHRISE_APIKEY, apiKey);
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
