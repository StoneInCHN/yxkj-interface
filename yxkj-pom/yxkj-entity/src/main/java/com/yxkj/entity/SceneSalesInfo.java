package com.yxkj.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 优享空间销售统计
 * 
 * @author Andrea
 * @version 2017年9月20日 下午6:09:22
 */
@Entity
@Table(name = "t_scene_sales_info")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_scene_sales_info_sequence")
public class SceneSalesInfo extends BaseEntity {


  private static final long serialVersionUID = -1649056464007668623L;


  /**
   * 统计日期
   */
  private Date reportTime;

  /**
   * 当日用户数
   */
  private BigDecimal userCount;

  /**
   * 当日销售额
   */
  private BigDecimal saleAmount;

  /**
   * 当日订单数(已完成订单)
   */
  private BigDecimal orderCount;

  /**
   * 平均客单价
   */
  private BigDecimal avgPrice;

  /**
   * 重复购买率
   */
  private BigDecimal rePurRate;

  @Temporal(TemporalType.DATE)
  public Date getReportTime() {
    return reportTime;
  }

  public void setReportTime(Date reportTime) {
    this.reportTime = reportTime;
  }

  @Column(scale = 0, precision = 10)
  public BigDecimal getUserCount() {
    return userCount;
  }

  public void setUserCount(BigDecimal userCount) {
    this.userCount = userCount;
  }

  @Column(scale = 2, precision = 14)
  public BigDecimal getSaleAmount() {
    return saleAmount;
  }

  public void setSaleAmount(BigDecimal saleAmount) {
    this.saleAmount = saleAmount;
  }

  @Column(scale = 0, precision = 10)
  public BigDecimal getOrderCount() {
    return orderCount;
  }

  public void setOrderCount(BigDecimal orderCount) {
    this.orderCount = orderCount;
  }

  @Column(scale = 4, precision = 12)
  public BigDecimal getAvgPrice() {
    return avgPrice;
  }

  public void setAvgPrice(BigDecimal avgPrice) {
    this.avgPrice = avgPrice;
  }

  @Column(scale = 5, precision = 6)
  public BigDecimal getRePurRate() {
    return rePurRate;
  }

  public void setRePurRate(BigDecimal rePurRate) {
    this.rePurRate = rePurRate;
  }

}
