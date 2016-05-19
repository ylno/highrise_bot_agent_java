package com.algaworks.highrisehq.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author duncan
 */
public class SubjectData implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String value;
  private String fieldId;
  private String label;

  public SubjectData() {
  }

  public SubjectData(final String fieldId, final String value) {
    this.fieldId = fieldId;
    this.value = value;
  }

  public SubjectData(final String fieldId, final String value, final String label) {
    this(fieldId, value);
    this.label = label;
  }

  @XmlElement
  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  @XmlElement(name = "value")
  public String getValue() {
    return value;
  }

  public void setValue(final String value) {
    this.value = value;
  }

  @XmlElement(name = "subject_field_id")
  public String getFieldId() {
    return fieldId;
  }

  public void setFieldId(final String name) {
    this.fieldId = name;
  }

  @XmlElement(name = "subject_field_label")
  public String getLabel() {
    return label;
  }

  public void setLabel(final String label) {
    this.label = label;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final SubjectData other = (SubjectData) obj;
    if ((this.fieldId == null) ? (other.fieldId != null) : !this.fieldId.equals(other.fieldId)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 71 * hash + (this.fieldId != null ? this.fieldId.hashCode() : 0);
    return hash;
  }

  @Override
  public String toString() {
    return "SubjectData{" + "value=" + value + ", fieldId=" + fieldId + ", label=" + label + '}';
  }

}
