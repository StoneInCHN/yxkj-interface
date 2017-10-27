package com.yxkj.service.impl;

import javax.annotation.Resource;

import com.yxkj.entity.commonenum.CommonEnum;
import org.springframework.stereotype.Service;

import com.yxkj.entity.OrderItem;
import com.yxkj.dao.OrderItemDao;
import com.yxkj.service.OrderItemService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("orderItemServiceImpl")
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItem, Long>
    implements OrderItemService {

  private OrderItemDao orderItemDao;

  @Resource(name = "orderItemDaoImpl")
  public void setBaseDao(OrderItemDao orderItemDao) {
    super.setBaseDao(orderItemDao);
    this.orderItemDao = orderItemDao;
  }

  /**
   * 更新出货状态
   * @param orderItemId
   * @param shipmentStatus
   * @return
   */
  @Override
  public OrderItem updateOrderItemShipmentStatus(Long orderItemId,
      CommonEnum.ShipmentStatus shipmentStatus) {

    OrderItem orderItem = orderItemDao.find(orderItemId);
    if (shipmentStatus.equals(CommonEnum.ShipmentStatus.SHIPMENT_SUCCESS)){
      orderItem.setPickupStatus(CommonEnum.PickupStatus.PICKUP);
    }
    orderItem.setShipmentStatus(shipmentStatus);
    OrderItem newOrderItem = orderItemDao.merge(orderItem);

    return newOrderItem;
  }
}
