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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.ShelfOrderStatus;

/**
 * Entity - 无人货架订单
 * 
 * @author Andrea
 * @version 2017年9月25日 下午4:47:06
 */
@Entity
@Table(name = "t_shelf_order", indexes = {@Index(name = "snIndex", columnList = "sn"),
    @Index(name = "paymentTimeIndex", columnList = "paymentTime")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_shelf_order_sequence")
public class ShelfOrder extends BaseEntity {


  private static final long serialVersionUID = 1L;

  /**
   * 订单编号
   */
  private String sn;

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
  private ShelfOrderStatus status;

  /**
   * 订单项
   */
  private Set<ShelfOrderItem> shelfOrderItems = new HashSet<ShelfOrderItem>();

  /**
   * 商品数量
   */
  private Integer goodsCount;

  /**
   * 无人货架的使用公司
   */
  private Company comp;


  public ShelfOrderStatus getStatus() {
    return status;
  }

  public void setStatus(ShelfOrderStatus status) {
    this.status = status;
  }

  @JsonProperty
  @ManyToOne(fetch = FetchType.LAZY)
  public Company getComp() {
    return comp;
  }

  public void setComp(Company comp) {
    this.comp = comp;
  }

  @JsonProperty
  @ManyToOne(fetch = FetchType.LAZY)
  public Tourist getTourist() {
    return tourist;
  }

  public void setTourist(Tourist tourist) {
    this.tourist = tourist;
  }

  @JsonProperty
  public Integer getItemCount() {
    return itemCount;
  }

  public void setItemCount(Integer itemCount) {
    this.itemCount = itemCount;
  }

  @JsonProperty
  @OneToMany(mappedBy = "shelfOrder", cascade = CascadeType.ALL)
  public Set<ShelfOrderItem> getShelfOrderItems() {
    return shelfOrderItems;
  }

  public void setShelfOrderItems(Set<ShelfOrderItem> shelfOrderItems) {
    this.shelfOrderItems = shelfOrderItems;
  }

  @JsonProperty
  @Column(scale = 2, precision = 10)
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

  @JsonProperty
  public Date getPaymentTime() {
    return paymentTime;
  }

  public void setPaymentTime(Date paymentTime) {
    this.paymentTime = paymentTime;
  }


  @JsonProperty
  @Column(length = 30)
  public String getSn() {
    return sn;
  }


  public void setSn(String sn) {
    this.sn = sn;
  }

  @JsonProperty
  @Column(scale = 2, precision = 10)
  public BigDecimal getAmount() {
    return amount;
  }


  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @JsonProperty
  @Column(length = 20)
  public String getPaymentType() {
    return paymentType;
  }


  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

  @JsonProperty
  @Transient
  public Integer getGoodsCount() {
    int count = 0;
    for (ShelfOrderItem shelfOrderItem : shelfOrderItems) {
      count += shelfOrderItem.getCount();
    }
    return count;
  }

  public void setGoodsCount(Integer goodsCount) {
    this.goodsCount = goodsCount;
  }


}
