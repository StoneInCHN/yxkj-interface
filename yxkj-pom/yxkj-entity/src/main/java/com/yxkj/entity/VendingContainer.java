package com.yxkj.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.CommonStatus;

/**
 * Entity - 货柜
 * 
 * @author Andrea
 * @version 2017年9月18日 下午6:19:04
 */
@Entity
@Table(name = "t_vending_container")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_vending_container_sequence")
public class VendingContainer extends BaseEntity {

  private static final long serialVersionUID = -5143795184127147282L;

  /**
   * 货柜编号(中控货柜则是imei编号)
   */
  private String sn;

  /**
   * 货柜唯一识别码
   */
  private String idCode;

  /**
   * 货柜二维码url
   */
  private String qrCodeUrl;
  /**
   * 优享空间
   */
  private Scene scene;

  /**
   * 状态
   */
  private CommonStatus status;

  /**
   * 重启间隔天数
   */
  private Integer rebootDay;

  /**
   * 默认重启时间
   */
  private String rebootTime;

  /**
   * 音量
   */
  private String volume;

  /**
   * 货柜类型
   */
  private ContainerCategory category;

  /** 中控货柜 */
  private VendingContainer parent;

  /** 下级子货柜 */
  private Set<VendingContainer> children = new HashSet<VendingContainer>();


  /**
   * 管家ID
   */
  private Long keeperId;

  // /**
  // * 极光push注册ID
  // */
  // private String jpushRegId;

  /**
   * 货道
   */
  private Set<ContainerChannel> cntrChannel = new HashSet<ContainerChannel>();

  @ManyToOne(fetch = FetchType.LAZY)
  public VendingContainer getParent() {
    return parent;
  }

  public void setParent(VendingContainer parent) {
    this.parent = parent;
  }

  @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  @OrderBy("sn asc")
  public Set<VendingContainer> getChildren() {
    return children;
  }

  public void setChildren(Set<VendingContainer> children) {
    this.children = children;
  }

  @OneToMany(mappedBy = "cntr")
  public Set<ContainerChannel> getCntrChannel() {
    return cntrChannel;
  }

  public void setCntrChannel(Set<ContainerChannel> cntrChannel) {
    this.cntrChannel = cntrChannel;
  }

  // @Column(length = 100)
  // public String getJpushRegId() {
  // return jpushRegId;
  // }
  //
  // public void setJpushRegId(String jpushRegId) {
  // this.jpushRegId = jpushRegId;
  // }


  public Long getKeeperId() {
    return keeperId;
  }

  public void setKeeperId(Long keeperId) {
    this.keeperId = keeperId;
  }

  @Column(length = 50)
  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  @Column(length = 50)
  public String getIdCode() {
    return idCode;
  }

  public void setIdCode(String idCode) {
    this.idCode = idCode;
  }

  @Column(length = 200)
  public String getQrCodeUrl() {
    return qrCodeUrl;
  }

  public void setQrCodeUrl(String qrCodeUrl) {
    this.qrCodeUrl = qrCodeUrl;
  }

  @ManyToOne
  public ContainerCategory getCategory() {
    return category;
  }

  public void setCategory(ContainerCategory category) {
    this.category = category;
  }


  public CommonStatus getStatus() {
    return status;
  }

  public void setStatus(CommonStatus status) {
    this.status = status;
  }


  public Integer getRebootDay() {
    return rebootDay;
  }

  public void setRebootDay(Integer rebootDay) {
    this.rebootDay = rebootDay;
  }

  @Column(length = 5)
  public String getVolume() {
    return volume;
  }

  public void setVolume(String volume) {
    this.volume = volume;
  }

  @Column(length = 20)
  public String getRebootTime() {
    return rebootTime;
  }

  public void setRebootTime(String rebootTime) {
    this.rebootTime = rebootTime;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public Scene getScene() {
    return scene;
  }

  public void setScene(Scene scene) {
    this.scene = scene;
  }



}
