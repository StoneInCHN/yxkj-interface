package com.yxkj.service;

import com.yxkj.entity.OrderItem;
import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.framework.service.BaseService;

public interface OrderItemService extends BaseService<OrderItem, Long> {

  /**
   * 更新出货状态
   * 
   * @param orderItemId
   * @param shipmentStatus
   * @return
   */
  OrderItem updateOrderItemShipmentStatus(Long orderItemId,
      CommonEnum.ShipmentStatus shipmentStatus);
}
