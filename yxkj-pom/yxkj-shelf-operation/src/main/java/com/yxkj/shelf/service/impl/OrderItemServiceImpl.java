package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.OrderItem;
import com.yxkj.shelf.dao.OrderItemDao;
import com.yxkj.shelf.service.OrderItemService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("orderItemServiceImpl")
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItem,Long> implements OrderItemService {

      @Resource(name="orderItemDaoImpl")
      public void setBaseDao(OrderItemDao orderItemDao) {
         super.setBaseDao(orderItemDao);
  }
}