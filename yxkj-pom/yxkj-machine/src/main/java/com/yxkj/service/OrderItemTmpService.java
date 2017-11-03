package com.yxkj.service;

import com.yxkj.entity.ContainerChannel;
import com.yxkj.entity.OrderItem;
import com.yxkj.entity.OrderItemTmp;
import com.yxkj.framework.service.BaseService;

public interface OrderItemTmpService extends BaseService<OrderItemTmp, Long> {

  void updateAfterPay(OrderItem orderItem,ContainerChannel containerChannel);
}
