package com.algaworks.highrisehq.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author duncan
 */
public class SubjectDatas {

  private String type = "array";
  private List<SubjectData> datas = new ArrayList<>();

  @XmlElement(name = "subject_data")
  public List<SubjectData> getDatas() {
    return datas;
  }

  public void setDatas(final List<SubjectData> datas) {
    this.datas = datas;
  }

  @XmlAttribute
  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public void removeData(final SubjectData subjectData) {
    datas.stream().filter(sd -> sd.getFieldId().equals(subjectData.getFieldId())).forEach(sd -> {
      sd.setId(-sd.getId());
      sd.setValue("");
    });
  }

}
