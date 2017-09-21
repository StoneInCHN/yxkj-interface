package com.yxkj.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 商品销售情况
 * 
 * @author Andrea
 * @version 2017年9月20日 上午11:22:42
 */
@Entity
@Table(name = "t_goods_sale_info",
    indexes = {@Index(name = "sceneIdIndex", columnList = "sceneId")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_goods_sale_info_sequence")
public class GoodsSaleInfo extends BaseEntity {

  private static final long serialVersionUID = -2158109459123036967L;

  /**
   * 商品类别
   */
  private GoodsCategory category;

  /**
   * 商品名称
   */
  private String name;

  /**
   * 商品编号
   */
  private String sn;


  /**
   * 默认销售价
   */
  private BigDecimal salePrice;

  /**
   * 优享空间名称
   */
  private String sceneName;

  /**
   * 优享空间ID
   */
  private Long sceneId;

  /**
   * 商品点击量
   */
  private Long clickCount;

  /**
   * 商品销量
   */
  private Long saleCount;

  /**
   * 商品销售额
   */
  private BigDecimal saleAmount;

  /**
   * 统计日期
   */
  private Date reportTime;


  @Temporal(TemporalType.DATE)
  public Date getReportTime() {
    return reportTime;
  }

  public void setReportTime(Date reportTime) {
    this.reportTime = reportTime;
  }


  public Long getClickCount() {
    return clickCount;
  }

  public void setClickCount(Long clickCount) {
    this.clickCount = clickCount;
  }

  public Long getSaleCount() {
    return saleCount;
  }

  public void setSaleCount(Long saleCount) {
    this.saleCount = saleCount;
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


  @Column(scale = 2, precision = 10)
  public BigDecimal getSalePrice() {
    return salePrice;
  }

  public void setSalePrice(BigDecimal salePrice) {
    this.salePrice = salePrice;
  }

  @Column(length = 100)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(length = 50)
  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public GoodsCategory getCategory() {
    return category;
  }

  public void setCategory(GoodsCategory category) {
    this.category = category;
  }


}
