package com.yxkj.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.OrderStatus;
import com.yxkj.entity.commonenum.CommonEnum.PurMethod;

/**
 * Entity - 用户订单
 *
 * @author Andrea
 * @version 2017年9月18日 上午10:42:49
 */
@Entity
@Table(name = "t_order", indexes = {@Index(name = "sceneIdIndex", columnList = "sceneId"),
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
   * 订单所属游客
   */
  private Tourist tourist;

  /**
   * 订单项数量
   */
  private Integer itemCount;

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

  /**
   * 购买方式
   */
  private PurMethod purMethod;


  /**
   * 订单项
   */
  private Set<OrderItem> orderItems = new HashSet<OrderItem>();

  /**
   * 优享空间名称
   */
  private String sceneName;

  /**
   * 优享空间ID
   */
  private Long sceneId;

  /**
   * 中控设备号
   */
  private String deviceNo;

  /**
   * 分润点(物业)
   */
  private BigDecimal fenRunPoint;

  /**
   * 物业分成
   */
  private BigDecimal fenRunAmount;

  @Column(scale = 4, precision = 10)
  public BigDecimal getFenRunPoint() {
    return fenRunPoint;
  }

  public void setFenRunPoint(BigDecimal fenRunPoint) {
    this.fenRunPoint = fenRunPoint;
  }

  @Column(scale = 6, precision = 16)
  public BigDecimal getFenRunAmount() {
    return fenRunAmount;
  }



  public void setFenRunAmount(BigDecimal fenRunAmount) {
    this.fenRunAmount = fenRunAmount;
  }



  @Column(length = 50)
  public String getSceneName() {
    return sceneName;
  }



  public PurMethod getPurMethod() {
    return purMethod;
  }

  public void setPurMethod(PurMethod purMethod) {
    this.purMethod = purMethod;
  }

  public Date getPaymentTime() {
    return paymentTime;
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

  @ManyToOne(fetch = FetchType.LAZY)
  public Tourist getTourist() {
    return tourist;
  }

  public void setTourist(Tourist tourist) {
    this.tourist = tourist;
  }

  public Integer getItemCount() {
    return itemCount;
  }

  public void setItemCount(Integer itemCount) {
    this.itemCount = itemCount;
  }

  @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL)
  public Set<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(Set<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  @Column(scale = 2, precision = 10)
  public BigDecimal getProfit() {
    return profit;
  }

  public void setProfit(BigDecimal profit) {
    this.profit = profit;
  }

  @Column(length = 10)
  public String getPaymentTypeId() {
    return paymentTypeId;
  }

  public void setPaymentTypeId(String paymentTypeId) {
    this.paymentTypeId = paymentTypeId;
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


  @Column(scale = 2, precision = 10)
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


  @Column(length = 50)
  public String getDeviceNo() {
    return deviceNo;
  }

  public void setDeviceNo(String deviceNo) {
    this.deviceNo = deviceNo;
  }
}
