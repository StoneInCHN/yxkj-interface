package com.yxkj.shelf.service;

import java.util.List;

import com.yxkj.entity.ShelfOrder;
import com.yxkj.entity.commonenum.CommonEnum.ShelfOrderStatus;
import com.yxkj.shelf.framework.service.BaseService;
import com.yxkj.shelf.json.beans.GoodsBean;

public interface ShelfOrderService extends BaseService<ShelfOrder, Long> {

  /**
   * 创建无人货架订单
   * 
   * @param payType
   * @param userName
   * @param compId
   * @param goodsBeans
   * @return
   */
  ShelfOrder createShelfOrder(String payType, String userName, Long compId,
      List<GoodsBean> goodsBeans);

  /**
   * 支付后订单回调
   * 
   * @param orderSn
   * @return
   */
  ShelfOrder callbackAfterPay(String orderSn);


  /**
   * 根据订单号查询订单
   * 
   * @param orderSn
   * @return
   */
  ShelfOrder getShelfOrderBySn(String orderSn);


  /**
   * 根据订单状态查询订单
   * 
   * @param status
   * @return
   */
  List<ShelfOrder> getShelfOrderByStatus(ShelfOrderStatus status);
}
