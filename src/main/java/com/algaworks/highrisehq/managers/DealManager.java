package com.algaworks.highrisehq.managers;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import com.algaworks.highrisehq.Highrise;
import com.algaworks.highrisehq.bean.Deal;
import com.algaworks.highrisehq.bean.ListWrapper;
import com.sun.jersey.api.client.WebResource;

/**
 *
 * @author duncan
 */
public class DealManager extends HighriseManager {

  public DealManager(final WebResource webResource, final String authorization) {
    super(webResource, authorization);
  }

  @Override
  public <T, W extends ListWrapper<T>> List<T> getAsList(
      final Class<T> objectType, final Class<W> listWrapType,
      final String path, final MultivaluedMap<String, String> params) {
    return super.getAsList(objectType, listWrapType, path, params);
  }

  public void update(final Deal deal) {
    this.update(deal, Highrise.DEAL_UPDATE_PATH.replaceAll("#\\{id\\}", deal.getId().toString()));
  }

}
