package com.yxkj.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 无人货架订单详情
 * 
 * @author Andrea
 * @version 2017年9月25日 下午5:01:20
 */
@Entity
@Table(name = "t_shelf_order_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_shelf_order_item_sequence")
public class ShelfOrderItem extends BaseEntity {

  private static final long serialVersionUID = -2197390136625086557L;

  /**
   * 所属订单
   */
  private ShelfOrder shelfOrder;

  /**
   * 商品实际单价
   */
  private BigDecimal price;

  /**
   * 商品实际总价
   */
  private BigDecimal totalPrice;

  /**
   * 商品名称
   */
  private String gName;

  /**
   * 商品规格
   */
  private String spec;

  /**
   * 商品编号
   */
  private String gSn;


  /**
   * 商品数量
   */
  private Integer count;


  @JsonProperty
  public String getSpec() {
    return spec;
  }


  public void setSpec(String spec) {
    this.spec = spec;
  }

  @Transient
  public BigDecimal getTotalPrice() {
    return price.multiply(new BigDecimal(count));
  }


  // public void setTotalPrice(BigDecimal totalPrice) {
  // this.totalPrice = totalPrice;
  // }

  @JsonProperty
  public Integer getCount() {
    return count;
  }


  public void setCount(Integer count) {
    this.count = count;
  }

  @JsonProperty
  @Column(length = 100)
  public String getgName() {
    return gName;
  }


  public void setgName(String gName) {
    this.gName = gName;
  }
  
  @JsonProperty
  @Column(length = 50)
  public String getgSn() {
    return gSn;
  }


  public void setgSn(String gSn) {
    this.gSn = gSn;
  }


  @JsonProperty
  @Column(scale = 2, precision = 10)
  public BigDecimal getPrice() {
    return price;
  }


  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public ShelfOrder getShelfOrder() {
    return shelfOrder;
  }


  public void setShelfOrder(ShelfOrder shelfOrder) {
    this.shelfOrder = shelfOrder;
  }

}
