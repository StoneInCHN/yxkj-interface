package com.yxkj.dao;

import com.yxkj.entity.Order;
import com.yxkj.framework.dao.BaseDao;

public interface OrderDao extends BaseDao<Order, Long> {
  /**
   * 根据订单号查询订单
   * 
   * @param orderSn
   * @return
   */
  Order getOrderBySn(String orderSn);
}
