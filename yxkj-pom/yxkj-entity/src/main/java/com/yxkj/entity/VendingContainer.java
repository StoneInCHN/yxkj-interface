package com.yxkj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
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
   * 货柜编号
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

  /**
   * 货柜分组编号
   */
  private String groupId;

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

  public ContainerCategory getCategory() {
    return category;
  }

  public void setCategory(ContainerCategory category) {
    this.category = category;
  }

  @Column(length = 50)
  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
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
