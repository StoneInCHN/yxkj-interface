package com.yxkj.service;

import com.yxkj.entity.OrderItem;
import com.yxkj.framework.service.BaseService;

public interface OrderItemService extends BaseService<OrderItem, Long> {
  /**
   * 订单退款
   * 
   * @param refundOrderItem
   */
  public void refund(OrderItem refundOrderItem);
}
