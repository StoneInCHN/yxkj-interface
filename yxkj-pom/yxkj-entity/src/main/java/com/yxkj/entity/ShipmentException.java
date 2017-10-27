package com.yxkj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.entity.commonenum.CommonEnum.ShipmentExcpType;

/**
 * Entity - 出货异常记录
 * 
 * @author Andrea
 * @version 2017年9月20日 下午12:27:18
 */
@Entity
@Table(name = "t_shipment_excp", indexes = {@Index(name = "sceneIdIndex", columnList = "sceneId")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_shipment_excp_sequence")
public class ShipmentException extends BaseEntity {

  private static final long serialVersionUID = -2197390136625086557L;


  /**
   * 优享空间名称
   */
  private String sceneName;

  /**
   * 优享空间ID
   */
  private Long sceneId;
  // /**
  // * 商品实际金额
  // */
  // private BigDecimal price;

  /**
   * 商品名称
   */
  private String gName;

  /**
   * 商品编号
   */
  private String gSn;

  /**
   * 商品ID
   */
  private Long gId;

  /**
   * 货柜ID
   */
  private Long cntrId;

  /**
   * 货柜编号
   */
  private String cntrSn;

  /**
   * 货道编号
   */
  private String channelSn;

  /**
   * 异常类型
   */
  private ShipmentExcpType excpType;

  /**
   * 异常原因
   */
  private CommonEnum.ExcpReason excpReason;

  @Column(length = 50)
  public String getSceneName() {
    return sceneName;
  }


  public void setSceneName(String sceneName) {
    this.sceneName = sceneName;
  }


  public Long getSceneId() {
    return sceneId;
  }


  public void setSceneId(Long sceneId) {
    this.sceneId = sceneId;
  }


  public ShipmentExcpType getExcpType() {
    return excpType;
  }


  public void setExcpType(ShipmentExcpType excpType) {
    this.excpType = excpType;
  }


  @Column(length = 100)
  public String getgName() {
    return gName;
  }


  public void setgName(String gName) {
    this.gName = gName;
  }

  @Column(length = 50)
  public String getgSn() {
    return gSn;
  }


  public void setgSn(String gSn) {
    this.gSn = gSn;
  }


  public Long getgId() {
    return gId;
  }


  public void setgId(Long gId) {
    this.gId = gId;
  }


  public Long getCntrId() {
    return cntrId;
  }


  public void setCntrId(Long cntrId) {
    this.cntrId = cntrId;
  }

  @Column(length = 50)
  public String getCntrSn() {
    return cntrSn;
  }


  public void setCntrSn(String cntrSn) {
    this.cntrSn = cntrSn;
  }

  @Column(length = 50)
  public String getChannelSn() {
    return channelSn;
  }


  public void setChannelSn(String channelSn) {
    this.channelSn = channelSn;
  }

  public CommonEnum.ExcpReason getExcpReason() {
    return excpReason;
  }

  public void setExcpReason(CommonEnum.ExcpReason excpReason) {
    this.excpReason = excpReason;
  }


  // @Column(scale = 2, precision = 10)
  // public BigDecimal getPrice() {
  // return price;
  // }
  //
  //
  // public void setPrice(BigDecimal price) {
  // this.price = price;
  // }

}
