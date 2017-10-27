package com.yxkj.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.CommonStatus;

/**
 * Entity - 优享空间场景
 * 
 * @author Andrea
 * @version 2017年9月18日 下午6:09:22
 */
@Entity
@Table(name = "t_scene")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_scene_sequence")
public class Scene extends BaseEntity {


  private static final long serialVersionUID = -1649056464007668623L;
  /**
   * 无人售货机货柜
   */
  private List<VendingContainer> vendingContainer = new ArrayList<VendingContainer>();

  /**
   * 空间编号
   */
  private String sn;

  /**
   * 空间地址名称
   */
  private String name;

  /**
   * 开通时间
   */
  private Date openTime;

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
   * 货柜总数
   */
  private Integer cntrTotalCount;

  /**
   * 中控数
   */
  private Integer conCount;

  /**
   * 冷柜数
   */
  private Integer freezeCntrCount;

  /**
   * 常温柜数
   */
  private Integer normalCntrCount;

  /**
   * 格子柜数
   */
  private Integer gridCntrCount;


  /**
   * 是否含有微仓
   */
  private Boolean hasStore;
  
  /**
   * 删除状态，修改为"不可用 "为逻辑删除
   */
  private CommonStatus removeStatus;

  /**
   * 管家
   */
  private ContainerKeeper cntrKeeper;
  /**
   * 物业
   */
  private PropertyKeeper propertyKeeper; 
  

  @ManyToOne(fetch = FetchType.LAZY)
  public ContainerKeeper getCntrKeeper() {
    return cntrKeeper;
  }

  public void setCntrKeeper(ContainerKeeper cntrKeeper) {
    this.cntrKeeper = cntrKeeper;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public PropertyKeeper getPropertyKeeper() {
	return propertyKeeper;
  }

  public void setPropertyKeeper(PropertyKeeper propertyKeeper) {
	this.propertyKeeper = propertyKeeper;
  }

  @Column(length = 30)
  @JsonProperty
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getHasStore() {
    return hasStore;
  }

  public void setHasStore(Boolean hasStore) {
    this.hasStore = hasStore;
  }



  public Integer getCntrTotalCount() {
    return cntrTotalCount;
  }

  public void setCntrTotalCount(Integer cntrTotalCount) {
    this.cntrTotalCount = cntrTotalCount;
  }

  public Integer getConCount() {
    return conCount;
  }

  public void setConCount(Integer conCount) {
    this.conCount = conCount;
  }

  public Integer getFreezeCntrCount() {
    return freezeCntrCount;
  }

  public void setFreezeCntrCount(Integer freezeCntrCount) {
    this.freezeCntrCount = freezeCntrCount;
  }

  public Integer getNormalCntrCount() {
    return normalCntrCount;
  }

  public void setNormalCntrCount(Integer normalCntrCount) {
    this.normalCntrCount = normalCntrCount;
  }

  public Integer getGridCntrCount() {
    return gridCntrCount;
  }

  public void setGridCntrCount(Integer gridCntrCount) {
    this.gridCntrCount = gridCntrCount;
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


  public Date getOpenTime() {
    return openTime;
  }

  public void setOpenTime(Date openTime) {
    this.openTime = openTime;
  }


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


  @Column(length = 30)
  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  @OneToMany(mappedBy = "scene")
  public List<VendingContainer> getVendingContainer() {
	return vendingContainer;
  }

  public void setVendingContainer(List<VendingContainer> vendingContainer) {
	this.vendingContainer = vendingContainer;
  }

  public CommonStatus getRemoveStatus() {
	return removeStatus;
  }

  public void setRemoveStatus(CommonStatus removeStatus) {
	this.removeStatus = removeStatus;
  }

  
}
