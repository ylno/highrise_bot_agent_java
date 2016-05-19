package com.algaworks.highrisehq.managers;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author duncan
 */
@XmlRootElement(name = "tag")
public class TagResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;
  private String name;

  public TagResponse() {
  }

  public TagResponse(final String id, final String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

}
