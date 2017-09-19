package com.yxkj.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 用户订单详情
 * 
 * @author Andrea
 * @version 2017年9月19日 上午10:19:50
 */
@Entity
@Table(name = "t_order_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_order_item_sequence")
public class OrderItem extends BaseEntity {

  private static final long serialVersionUID = -2197390136625086557L;

  /**
   * 所属订单
   */
  private Order userOrder;
  /**
   * 所属商品
   */
  private Goods goods;

  /**
   * 商品实际金额
   */
  private BigDecimal price;


  @ManyToOne
  public Order getUserOrder() {
    return userOrder;
  }


  public void setUserOrder(Order userOrder) {
    this.userOrder = userOrder;
  }


  @ManyToOne
  public Goods getGoods() {
    return goods;
  }


  public void setGoods(Goods goods) {
    this.goods = goods;
  }


  @Column(scale = 2, precision = 10)
  public BigDecimal getPrice() {
    return price;
  }


  public void setPrice(BigDecimal price) {
    this.price = price;
  }

}
