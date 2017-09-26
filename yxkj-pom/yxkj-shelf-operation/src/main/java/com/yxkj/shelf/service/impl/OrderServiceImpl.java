package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.Order;
import com.yxkj.shelf.dao.OrderDao;
import com.yxkj.shelf.service.OrderService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("orderServiceImpl")
public class OrderServiceImpl extends BaseServiceImpl<Order,Long> implements OrderService {

      @Resource(name="orderDaoImpl")
      public void setBaseDao(OrderDao orderDao) {
         super.setBaseDao(orderDao);
  }
}