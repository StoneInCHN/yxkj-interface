package com.yxkj.entity;


import javax.persistence.*;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 退款记录表
 *
 * @author Andrea
 * @version 2017年9月18日 上午10:42:49
 */
@Entity
@Table(name = "t_refund_history")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_refund_history_sequence")
public class RefundHistory extends BaseEntity {

  /**
   * 订单
   */
  private Order refundOrder;

  /**
   * 退款单号
   */
  private String refundSn;

  /**
   * 退款金额
   */
  private String refundAmount;

  /**
   * 支付方式
   */
  private String paymentType;

  @ManyToOne
  public Order getRefundOrder() {
    return refundOrder;
  }

  public void setRefundOrder(Order refundOrder) {
    this.refundOrder = refundOrder;
  }

  @Column(length = 32)
  public String getRefundSn() {
    return refundSn;
  }

  public void setRefundSn(String refundSn) {
    this.refundSn = refundSn;
  }

  @Column(scale = 2, precision = 10)
  public String getRefundAmount() {
    return refundAmount;
  }

  public void setRefundAmount(String refundAmount) {
    this.refundAmount = refundAmount;
  }

  @Column(length = 20)
  public String getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }
}
