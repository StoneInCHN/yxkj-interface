package com.yxkj.service;

import java.math.BigDecimal;

import com.yxkj.entity.Order;
import com.yxkj.framework.service.BaseService;

public interface OrderService extends BaseService<Order, Long> {

  /**
   * 按复购次数计算重复购买率 公式:重复购买人数/总购买人数
   * 
   * @param count
   * @return
   */
  BigDecimal calRePurRate(Integer count, Long sceneId);
}
