package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.OrderItemTmp;
import com.yxkj.dao.OrderItemTmpDao;
import com.yxkj.service.OrderItemTmpService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("orderItemTmpServiceImpl")
public class OrderItemTmpServiceImpl extends BaseServiceImpl<OrderItemTmp,Long> implements OrderItemTmpService {

      @Resource(name="orderItemTmpDaoImpl")
      private OrderItemTmpDao orderItemTmpDao;

      @Resource(name="orderItemTmpDaoImpl")
      public void setBaseDao(OrderItemTmpDao orderItemTmpDao) {
         super.setBaseDao(orderItemTmpDao);
      }
}