package com.algaworks.highrisehq.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author thiagofa
 *
 */
@XmlRootElement(name = "people")
public class People implements Serializable, ListWrapper<Person> {

  private static final long serialVersionUID = 1L;

  private List<Person> people;

  @Override
  @XmlElement(name = "person")
  public List<Person> getObjects() {
    return people;
  }

  @Override
  public void setObjects(final List<Person> people) {
    this.people = people;
  }

}
