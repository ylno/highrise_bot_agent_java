package com.algaworks.highrisehq.bean;

import java.io.Serializable;

/**
 * 
 * @author thiagofa
 *
 */
public class PhoneNumber implements Serializable {

  private static final long serialVersionUID = 1L;
  public static String LOCATION_WORK = "Work";
  public static String LOCATION_MOBILE = "Mobile";

  private Long id;
  private String number;
  private String location;

  public PhoneNumber() {
  }

  public PhoneNumber(final String number, final String location) {
    super();
    this.number = number;
    this.location = location;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(final String number) {
    this.number = number;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(final String location) {
    this.location = location;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final PhoneNumber other = (PhoneNumber) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
