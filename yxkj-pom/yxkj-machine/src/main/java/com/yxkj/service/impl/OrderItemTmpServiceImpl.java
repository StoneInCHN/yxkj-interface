package com.yxkj.service.impl;

import javax.annotation.Resource;

import com.yxkj.service.OrderItemTmpService;
import org.springframework.stereotype.Service;

import com.yxkj.dao.*;
import com.yxkj.entity.*;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("orderItemTmpServiceImpl")
public class OrderItemTmpServiceImpl extends BaseServiceImpl<OrderItemTmp, Long>
    implements OrderItemTmpService {
  private OrderItemTmpDao orderItemTmpDao;


  @Resource(name = "orderItemTmpDaoImpl")
  public void setBaseDao(OrderItemTmpDao orderItemTmpDao) {
    super.setBaseDao(orderItemTmpDao);
    this.orderItemTmpDao = orderItemTmpDao;
  }


  @Override
  public void updateAfterPay(OrderItem orderItem, ContainerChannel containerChannel) {

    Order order = orderItem.getUserOrder();

    OrderItemTmp orderItemTmp = orderItemTmpDao.getOrderItemTmpByItemId(orderItem.getId());
    //如果为null，表示超过30秒，数据已经被MySQL event处理，无需再更新临时订单表
    if (orderItemTmp == null)
      return;
    orderItemTmpDao.remove(orderItemTmp);
    switch (order.getPurMethod()) {
      case CONTROLL_MACHINE:
        containerChannel.setOfflineLocalLock(containerChannel.getOfflineLocalLock() - 1);
        break;
      case SCAN_CODE:
      case INPUT_CODE:
        containerChannel.setOfflineRemoteLock(containerChannel.getOfflineRemoteLock() - 1);
        break;
    }
  }
}
