package com.yxkj.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yxkj.dao.OrderDao;
import com.yxkj.entity.Order;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.base.ResponseMultiple;
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
  public BigDecimal calRePurRate(Integer count, Long sceneId, Date startTime, Date endTime) {
    BigDecimal calRate = new BigDecimal(0);
    BigDecimal total = orderDao.getPurTotal(sceneId, startTime, endTime);
    BigDecimal repeatPur = orderDao.getPurRepeat(count, sceneId, startTime, endTime);
    if (total != null && repeatPur != null && total.compareTo(new BigDecimal(0)) > 0) {
      calRate = repeatPur.divide(total, 4, BigDecimal.ROUND_HALF_UP);
    }
    return calRate;
  }

  @Override
  public List<Map<String, Object>> salesGraphDataMap(Date startTime, Date endTime) {
    return orderDao.salesGraphDataMap(startTime, endTime);
  }

  @Override
  public List<Map<String, Object>> salesGraphUserCountMap(Date startTime, Date endTime) {
    return orderDao.salesGraphUserCountMap(startTime, endTime);
  }

  @Override
  public List<Map<String, Object>> salesGraphRepeatRateMap(Integer repeatCount, Date startTime,
      Date endTime) {
    return orderDao.salesGraphRepeatRateMap(repeatCount, startTime, endTime);
  }

  @Override
  public ResponseMultiple<Map<String, Object>> salesListDataMap(Long sceneId, Date startTime,
      Date endTime, Integer pageSize, Integer pageNum) {
    return orderDao.salesListDataMap(sceneId, startTime, endTime, pageSize, pageNum);
  }
}
