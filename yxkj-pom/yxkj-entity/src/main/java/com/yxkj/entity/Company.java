package com.yxkj.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 使用无人货架的公司
 * 
 * @author Andrea
 * @version 2017年9月25日 上午10:47:41
 */
@Entity
@Table(name = "t_comp")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_comp_sequence")
public class Company extends BaseEntity {

  private static final long serialVersionUID = 2943757220535720270L;


  /**
   * 公司编号
   */
  private String sn;

  /**
   * 公司全名
   */
  private String fullName;

  /**
   * 公司展示名称
   */
  private String displayName;


  /**
   * 联系人
   */
  private String contactPerson;

  /**
   * 联系电话
   */
  private String contactPhone;

  /**
   * 地区
   */
  private Area area;

  /**
   * 详细地址
   */
  private String address;

  /**
   * 经度
   */
  private BigDecimal longitude;

  /**
   * 纬度
   */
  private BigDecimal latitude;


  /**
   * 备注
   */
  private String remark;

  /**
   * 货架
   */
  private Set<CompanyShelf> goodsShelves = new HashSet<CompanyShelf>();

  @JsonProperty
  @OneToMany(mappedBy = "comp")
  public Set<CompanyShelf> getGoodsShelves() {
    return goodsShelves;
  }

  public void setGoodsShelves(Set<CompanyShelf> goodsShelves) {
    this.goodsShelves = goodsShelves;
  }

  @Column(length = 100)
  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  @Column(length = 50)
  public String getDisplayName() {
    return displayName;
  }
  @JsonProperty
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
  @JsonProperty
  @Column(length = 20)
  public String getContactPerson() {
    return contactPerson;
  }

  public void setContactPerson(String contactPerson) {
    this.contactPerson = contactPerson;
  }
  @JsonProperty
  @Column(length = 20)
  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  @Column(length = 200)
  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  @Column(scale = 12, precision = 16)
  public BigDecimal getLatitude() {
    return latitude;
  }

  public void setLatitude(BigDecimal latitude) {
    this.latitude = latitude;
  }

  @Column(scale = 12, precision = 16)
  public BigDecimal getLongitude() {
    return longitude;
  }

  public void setLongitude(BigDecimal longitude) {
    this.longitude = longitude;
  }

  @JsonProperty
  @Column(length = 100)
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public Area getArea() {
    return area;
  }

  public void setArea(Area area) {
    this.area = area;
  }

  @JsonProperty
  @Column(length = 30)
  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }



}
