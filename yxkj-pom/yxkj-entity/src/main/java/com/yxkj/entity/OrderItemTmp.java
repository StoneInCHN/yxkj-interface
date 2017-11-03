package com.yxkj.entity;

import javax.persistence.*;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 用户订单详情临时表 用于保存下单后，支付前的锁定的商品情况
 * 
 * @author Andrea
 * @version 2017年9月19日 上午10:19:50
 */
@Entity
@Table(name = "t_order_item_tmp")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_order_item_tmp_sequence")
@SuppressWarnings("unused")
public class OrderItemTmp extends BaseEntity {

  private static final long serialVersionUID = -2197390136625086557L;


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
   * 所属订单
   */
  private Order userOrder;

  /**
   * 对应订单项id
   */
  private Long orderItemId;
  @Column(length = 50)
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

  @ManyToOne(fetch = FetchType.LAZY)
  public Order getUserOrder() {
    return userOrder;
  }


  public void setUserOrder(Order userOrder) {
    this.userOrder = userOrder;
  }

  public Long getOrderItemId() {
    return orderItemId;
  }

  public void setOrderItemId(Long orderItemId) {
    this.orderItemId = orderItemId;
  }
}
