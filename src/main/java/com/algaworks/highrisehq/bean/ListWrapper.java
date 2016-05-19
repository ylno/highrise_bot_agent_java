package com.algaworks.highrisehq.bean;

import java.util.List;

/**
 * 
 * @author thiagofa
 *
 */
public interface ListWrapper<T> {

  List<T> getObjects();

  void setObjects(List<T> objects);

}
