package com.yxkj.shelf.dao;

import com.yxkj.entity.ShelfOrder;
import com.yxkj.shelf.framework.dao.BaseDao;

public interface ShelfOrderDao extends BaseDao<ShelfOrder, Long> {
  /**
   * 根据订单号查询订单
   * 
   * @param orderSn
   * @return
   */
  ShelfOrder getShelfOrderBySn(String orderSn);
}
