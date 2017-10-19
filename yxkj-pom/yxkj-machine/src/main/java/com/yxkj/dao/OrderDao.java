package com.yxkj.dao;

import java.util.List;

import com.yxkj.common.entity.CmdMsg;
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

  /**
   * 出货
   * 
   * @param orderId
   */
  List<CmdMsg> salesOut(Long orderId);
}
