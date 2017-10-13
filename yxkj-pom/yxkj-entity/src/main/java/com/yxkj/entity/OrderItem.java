package com.yxkj.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.PickupStatus;
import com.yxkj.entity.commonenum.CommonEnum.RefundStatus;
import com.yxkj.entity.commonenum.CommonEnum.ShipmentStatus;

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
  // /**
  // * 所属商品
  // */
  // private Goods goods;

  /**
   * 商品实际金额
   */
  private BigDecimal price;

  /**
   * 商品名称
   */
  private String gName;

  /**
   * 商品编号
   */
  private String gSn;

  /**
   * 商品规格
   */
  private String spec;


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
   * 取货状态
   */
  private PickupStatus pickupStatus;

  /**
   * 出货状态
   */
  private ShipmentStatus shipmentStatus;

  /**
   * 退款状态
   */
  private RefundStatus refundStatus;


  public String getSpec() {
    return spec;
  }


  public void setSpec(String spec) {
    this.spec = spec;
  }


  public PickupStatus getPickupStatus() {
    return pickupStatus;
  }


  public void setPickupStatus(PickupStatus pickupStatus) {
    this.pickupStatus = pickupStatus;
  }


  public ShipmentStatus getShipmentStatus() {
    return shipmentStatus;
  }


  public void setShipmentStatus(ShipmentStatus shipmentStatus) {
    this.shipmentStatus = shipmentStatus;
  }


  public RefundStatus getRefundStatus() {
    return refundStatus;
  }


  public void setRefundStatus(RefundStatus refundStatus) {
    this.refundStatus = refundStatus;
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


  // @ManyToOne(fetch = FetchType.LAZY)
  // public Goods getGoods() {
  // return goods;
  // }
  //
  //
  // public void setGoods(Goods goods) {
  // this.goods = goods;
  // }


  @Column(scale = 2, precision = 10)
  public BigDecimal getPrice() {
    return price;
  }


  public void setPrice(BigDecimal price) {
    this.price = price;
  }

}
