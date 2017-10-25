package com.yxkj.dao;

import java.math.BigDecimal;

import com.yxkj.entity.Order;
import com.yxkj.framework.dao.BaseDao;

public interface OrderDao extends BaseDao<Order, Long> {

  /**
   * 查询总购买人数(不重复人数)
   * 
   * @return
   */
  BigDecimal getPurTotal(Long sceneId);

  /**
   * 根据复购次数查询重复购买人数
   * 
   * @return
   */
  BigDecimal getPurRepeat(Integer count, Long sceneId);
}
