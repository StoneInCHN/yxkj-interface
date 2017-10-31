package com.yxkj.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 物业平台销售情况统计
 * 
 * @author Andrea
 * @version 2017年9月20日 上午11:22:42
 */
@Entity
@Table(name = "t_prokeeper_sale_info", indexes = {
    @Index(name = "sceneIdIndex", columnList = "sceneId"),
    @Index(name = "proKeeperIdIndex", columnList = "proKeeperId"),
    @Index(name = "reportTimeIndex", columnList = "reportTime")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_prokeeper_sale_info_sequence")
public class PropertyKeeperSalesReport extends BaseEntity {

  private static final long serialVersionUID = -2158109459123036967L;

  /**
   * 物业ID
   */
  private Long proKeeperId;
  /**
   * 分润点(物业)
   */
  private BigDecimal fenRunPoint;

  /**
   * 总分成
   */
  private BigDecimal profitAmount;
  /**
   * 优享空间名称
   */
  private String sceneName;

  /**
   * 优享空间ID
   */
  private Long sceneId;

  /**
   * 销售额
   */
  private BigDecimal saleAmount;

  /**
   * 统计日期
   */
  private Date reportTime;


  @Column(scale = 4, precision = 10)
  public BigDecimal getFenRunPoint() {
    return fenRunPoint;
  }

  public void setFenRunPoint(BigDecimal fenRunPoint) {
    this.fenRunPoint = fenRunPoint;
  }


  @Temporal(TemporalType.DATE)
  public Date getReportTime() {
    return reportTime;
  }

  public void setReportTime(Date reportTime) {
    this.reportTime = reportTime;
  }



  @Column(scale = 2, precision = 14)
  public BigDecimal getSaleAmount() {
    return saleAmount;
  }

  public void setSaleAmount(BigDecimal saleAmount) {
    this.saleAmount = saleAmount;
  }

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

  public Long getProKeeperId() {
    return proKeeperId;
  }

  public void setProKeeperId(Long proKeeperId) {
    this.proKeeperId = proKeeperId;
  }

  @Column(scale = 6, precision = 16)
  public BigDecimal getProfitAmount() {
    return profitAmount;
  }

  public void setProfitAmount(BigDecimal profitAmount) {
    this.profitAmount = profitAmount;
  }

}
