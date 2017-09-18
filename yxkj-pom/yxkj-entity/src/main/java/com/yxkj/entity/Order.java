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

import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.OrderStatus;

/**
 * Entity - 用户订单
 * 
 * @author Andrea
 * @version 2017年9月18日 上午10:42:49
 */
@Entity
@Table(name = "t_order", indexes = {@Index(name = "createDateIndex", columnList = "createDate"),
    @Index(name = "snIndex", columnList = "sn"),
    @Index(name = "paymentTimeIndex", columnList = "paymentTime")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_order_sequence")
public class Order extends BaseEntity {


  private static final long serialVersionUID = 1L;

  /**
   * 订单编号
   */
  private String sn;

  /**
   * 订单所属用户
   */
  private EndUser endUser;

  /**
   * 支付方式
   */
  private String paymentType;

  /**
   * 支付方式ID
   */
  private String paymentTypeId;

  /**
   * 支付时间
   */
  private Date paymentTime;

  /**
   * 消费金额
   */
  private BigDecimal amount;

  /**
   * 毛利
   */
  private BigDecimal profit;


  /**
   * 订单状态
   */
  private OrderStatus status;


  @Column(scale = 4, precision = 12)
  public BigDecimal getProfit() {
    return profit;
  }

  public void setProfit(BigDecimal profit) {
    this.profit = profit;
  }

  @Column(length = 5)
  public String getPaymentTypeId() {
    return paymentTypeId;
  }

  public void setPaymentTypeId(String paymentTypeId) {
    this.paymentTypeId = paymentTypeId;
  }


  public Date getPaymentTime() {
    return paymentTime;
  }

  public void setPaymentTime(Date paymentTime) {
    this.paymentTime = paymentTime;
  }


  public OrderStatus getStatus() {
    return status;
  }


  public void setStatus(OrderStatus status) {
    this.status = status;
  }


  @Column(length = 30)
  public String getSn() {
    return sn;
  }


  public void setSn(String sn) {
    this.sn = sn;
  }


  @Column(scale = 4, precision = 12)
  public BigDecimal getAmount() {
    return amount;
  }


  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @Column(length = 20)
  public String getPaymentType() {
    return paymentType;
  }


  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }


  @ManyToOne(fetch = FetchType.LAZY)
  public EndUser getEndUser() {
    return endUser;
  }


  public void setEndUser(EndUser endUser) {
    this.endUser = endUser;
  }


}
