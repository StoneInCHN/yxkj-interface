package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.Order;
import com.yxkj.dao.OrderDao;
import com.yxkj.service.OrderService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("orderServiceImpl")
public class OrderServiceImpl extends BaseServiceImpl<Order,Long> implements OrderService {

      @Resource(name="orderDaoImpl")
      public void setBaseDao(OrderDao orderDao) {
         super.setBaseDao(orderDao);
  }
}