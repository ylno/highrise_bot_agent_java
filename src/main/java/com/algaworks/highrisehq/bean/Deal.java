package com.algaworks.highrisehq.bean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author duncan
 */
@XmlRootElement(name = "deal")
public class Deal implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String accountId;
  private String background;
  private Long categoryId;
  private Date createDate;
  private String currency;
  private Integer duration;
  private Long groupId;
  private String name;
  private Long ownerId;
  private Long partyId;
  private Long price;
  private String priceType;
  private Long responsiblePartyId;
  private String status;
  private Date statusChangeDate;
  private Date updateDate;
  private String visibleTo;

  // <parties type="array">
  // <party>...</party>
  // </parties>
  // <party>...</party

  @XmlElement(name = "account-id")
  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(final String accountId) {
    this.accountId = accountId;
  }

  @XmlElement(name = "name")
  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @XmlElement(name = "background")
  public String getBackground() {
    return background;
  }

  public void setBackground(final String background) {
    this.background = background;
  }

  @XmlElement(name = "category-id")
  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(final Long categoryId) {
    this.categoryId = categoryId;
  }

  @XmlElement(name = "group-id")
  public Long getGroupId() {
    return groupId;
  }

  public void setGroupId(final Long groupId) {
    this.groupId = groupId;
  }

  @XmlElement(name = "id")
  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  @XmlElement(name = "party-id")
  public Long getPartyId() {
    return partyId;
  }

  public void setPartyId(final Long partyId) {
    this.partyId = partyId;
  }

  @XmlElement(name = "status")
  public String getStatus() {
    return status;
  }

  public void setStatus(final String status) {
    this.status = status;
  }

  @XmlElement(name = "created-at")
  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(final Date createDate) {
    this.createDate = createDate;
  }

  @XmlElement(name = "currency")
  public String getCurrency() {
    return currency;
  }

  public void setCurrency(final String currency) {
    this.currency = currency;
  }

  @XmlElement(name = "duration")
  public Integer getDuration() {
    return duration;
  }

  public void setDuration(final Integer duration) {
    this.duration = duration;
  }

  @XmlElement(name = "owner-id")
  public Long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(final Long ownerId) {
    this.ownerId = ownerId;
  }

  @XmlElement(name = "price")
  public Long getPrice() {
    return price;
  }

  public void setPrice(final Long price) {
    this.price = price;
  }

  @XmlElement(name = "price-type")
  public String getPriceType() {
    return priceType;
  }

  public void setPriceType(final String priceType) {
    this.priceType = priceType;
  }

  @XmlElement(name = "responsible-party-id")
  public Long getResponsiblePartyId() {
    return responsiblePartyId;
  }

  public void setResponsiblePartyId(final Long responsiblePartyId) {
    this.responsiblePartyId = responsiblePartyId;
  }

  @XmlElement(name = "status-changed-on")
  public Date getStatusChangeDate() {
    return statusChangeDate;
  }

  public void setStatusChangeDate(final Date statusChangeDate) {
    this.statusChangeDate = statusChangeDate;
  }

  @XmlElement(name = "update-at")
  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(final Date updateDate) {
    this.updateDate = updateDate;
  }

  @XmlElement(name = "visible-to")
  public String getVisibleTo() {
    return visibleTo;
  }

  public void setVisibleTo(final String visibleTo) {
    this.visibleTo = visibleTo;
  }

  @Override
  public String toString() {
    return "Deal{" + "id=" + id + ", accountId=" + accountId + ", categoryId=" + categoryId + ", name=" + name + ", status=" + status + '}';
  }

}
