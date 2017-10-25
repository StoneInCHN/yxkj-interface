package com.yxkj.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yxkj.dao.OrderDao;
import com.yxkj.entity.Order;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.service.OrderService;

@Service("orderServiceImpl")
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

  @Resource(name = "orderDaoImpl")
  private OrderDao orderDao;

  @Resource(name = "orderDaoImpl")
  public void setBaseDao(OrderDao orderDao) {
    super.setBaseDao(orderDao);
  }

  @Override
  public BigDecimal calRePurRate(Integer count, Long sceneId) {
    BigDecimal calRate = new BigDecimal(0);
    BigDecimal total = orderDao.getPurTotal(sceneId);
    BigDecimal repeatPur = orderDao.getPurRepeat(count, sceneId);
    if (total != null && repeatPur != null && total.compareTo(new BigDecimal(0)) > 0) {
      calRate = repeatPur.divide(total, 4, BigDecimal.ROUND_HALF_UP);
    }
    return calRate;
  }
}
