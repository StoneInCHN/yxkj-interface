package com.yxkj.service;

import java.util.List;

import com.yxkj.entity.Order;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.beans.GoodsBean;

public interface OrderService extends BaseService<Order, Long> {
  /**
   * 创建无人货架订单
   * 
   * @param payType
   * @param userName
   * @param imei
   * @param goodsBeans
   * @return
   */
  Order createCntrOrder(String payType, String userName, String imei, List<GoodsBean> goodsBeans);

  /**
   * 支付后订单回调
   * 
   * @param orderSn
   * @return
   */
  Order callbackAfterPay(String orderSn);

  /**
   * 根据订单号查询订单
   * 
   * @param orderSn
   * @return
   */
  Order getOrderBySn(String orderSn);

  /**
   * 出货
   */
  void salesOut(Long orderId);
}
