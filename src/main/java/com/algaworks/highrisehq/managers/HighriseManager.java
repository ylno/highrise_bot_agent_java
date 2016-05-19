package com.algaworks.highrisehq.managers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.algaworks.highrisehq.HighriseException;
import com.algaworks.highrisehq.bean.Empty;
import com.algaworks.highrisehq.bean.Errors;
import com.algaworks.highrisehq.bean.ListWrapper;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * 
 * @author thiagofa
 *
 */
public abstract class HighriseManager {

  private final WebResource webResource;
  private final String authorization;

  public HighriseManager(final WebResource webResource, final String authorization) {
    super();
    this.webResource = webResource;
    this.authorization = authorization;
  }

  protected <T> T show(final Class<T> entity, final String path) {
    T result = null;
    final ClientResponse response = this.get(path, null);

    final String s = response.getEntity(String.class);

    if (response.getStatus() == ClientResponse.Status.OK.getStatusCode()
        || response.getStatus() == ClientResponse.Status.BAD_REQUEST.getStatusCode()) {
      try {
        final JAXBContext jc = JAXBContext.newInstance(entity, Empty.class, Errors.class);
        final Unmarshaller unmarshaller = jc.createUnmarshaller();
        final Object obj = unmarshaller.unmarshal(new StringReader(s));
        if (obj instanceof Errors) {
          final Errors errors = (Errors) obj;
          throw new HighriseException(response.getStatus(), errors.getError());
        } else if (!(obj instanceof Empty)) {
          result = (T) obj;
        }
      } catch (final JAXBException e) {
        throw new HighriseException("Error unmarshalling Highrise return.", e);
      }
    } else {
      throw new HighriseException(response.getStatus(), "Highrise return not expected ("
          + ClientResponse.Status.fromStatusCode(response.getStatus()) + ").");
    }

    return result;
  }

  protected <T> void update(final T entity, final String path) {
    final ClientResponse response = this.put(path, entity);

    final String s = response.getEntity(String.class);

    if (response.getStatus() == ClientResponse.Status.BAD_REQUEST.getStatusCode()) {
      try {
        final JAXBContext jc = JAXBContext.newInstance(Errors.class);
        final Unmarshaller unmarshaller = jc.createUnmarshaller();
        final Object obj = unmarshaller.unmarshal(new StringReader(s));
        if (obj instanceof Errors) {
          final Errors errors = (Errors) obj;
          throw new HighriseException(response.getStatus(), errors.getError());
        }
      } catch (final JAXBException e) {
        throw new HighriseException("Error unmarshalling Highrise return.", e);
      }
    } else if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
      throw new HighriseException(response.getStatus(), "Highrise return not expected ("
          + ClientResponse.Status.fromStatusCode(response.getStatus()) + ").");
    }
  }

  @SuppressWarnings("unchecked")
  protected <T> T create(final T entity, final String path) {
    T result = null;
    final ClientResponse response = this.post(path, entity);

    final String s = response.getEntity(String.class);

    if (response.getStatus() == ClientResponse.Status.CREATED.getStatusCode()
        || response.getStatus() == ClientResponse.Status.BAD_REQUEST.getStatusCode()) {
      try {
        final JAXBContext jc = JAXBContext.newInstance(entity.getClass(), Empty.class, Errors.class);
        final Unmarshaller unmarshaller = jc.createUnmarshaller();
        final Object obj = unmarshaller.unmarshal(new StringReader(s));
        if (obj instanceof Errors) {
          final Errors errors = (Errors) obj;
          throw new HighriseException(response.getStatus(), errors.getError());
        } else if (!(obj instanceof Empty)) {
          result = (T) obj;
        }
      } catch (final JAXBException e) {
        throw new HighriseException("Error unmarshalling Highrise return.", e);
      }
    } else {
      throw new HighriseException(response.getStatus(), "Highrise return not expected ("
          + ClientResponse.Status.fromStatusCode(response.getStatus()) + ").");
    }

    return result;
  }

  @SuppressWarnings("unchecked")
  protected void remove(final String path) {
    final ClientResponse response = this.delete(path);
    if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
      throw new HighriseException(response.getStatus(), "Highrise return not expected ("
          + ClientResponse.Status.fromStatusCode(response.getStatus()) + ").");
    }
  }

  @SuppressWarnings("unchecked")
  protected <T, W extends ListWrapper<T>> List<T> getAsList(
      final Class<T> objectType, final Class<W> listWrapType, final String path,
      final MultivaluedMap<String, String> params) {
    List<T> result = new ArrayList<>();

    final ClientResponse response = this.get(path, params);

    if (response.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
      try {
        final String s = response.getEntity(String.class);
        // System.out.println(s);

        final JAXBContext jc = JAXBContext.newInstance(listWrapType, Empty.class);
        final Unmarshaller unmarshaller = jc.createUnmarshaller();
        final Object obj = unmarshaller.unmarshal(new StringReader(s));
        if (!(obj instanceof Empty)) {
          result = ((W) obj).getObjects();
        }
      } catch (final JAXBException e) {
        throw new HighriseException("Error unmarshalling Highrise return as list.", e);
      }
    } else {
      throw new HighriseException(response.getStatus(), "Highrise return not expected ("
          + ClientResponse.Status.fromStatusCode(response.getStatus()) + ").");
    }

    return result;
  }

  private WebResource.Builder getBuilder(final String path, MultivaluedMap<String, String> params) {
    if (params == null) {
      params = new MultivaluedMapImpl();
    }

    return webResource.path(path)
        .queryParams(params)
        .accept(MediaType.APPLICATION_XML)
        .header("Authorization", authorization);
  }

  private ClientResponse get(final String path, final MultivaluedMap<String, String> params) {
    final WebResource.Builder builder = this.getBuilder(path, params);

    return builder.get(ClientResponse.class);
  }

  protected ClientResponse post(final String path, final Object entity) {
    final WebResource.Builder builder = this.getBuilder(path, null);
    return builder.entity(entity).post(ClientResponse.class);
  }

  private ClientResponse put(final String path, final Object entity) {
    final WebResource.Builder builder = this.getBuilder(path, null);
    return builder.entity(entity).put(ClientResponse.class);
  }

  private ClientResponse delete(final String path) {
    final WebResource.Builder builder = this.getBuilder(path, null);
    return builder.delete(ClientResponse.class);
  }

}
