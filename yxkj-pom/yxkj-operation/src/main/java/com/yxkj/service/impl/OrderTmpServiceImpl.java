package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.OrderTmp;
import com.yxkj.dao.OrderTmpDao;
import com.yxkj.service.OrderTmpService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("orderTmpServiceImpl")
public class OrderTmpServiceImpl extends BaseServiceImpl<OrderTmp,Long> implements OrderTmpService {

      @Resource(name="orderTmpDaoImpl")
      private OrderTmpDao orderTmpDao;

      @Resource(name="orderTmpDaoImpl")
      public void setBaseDao(OrderTmpDao orderTmpDao) {
         super.setBaseDao(orderTmpDao);
      }
}