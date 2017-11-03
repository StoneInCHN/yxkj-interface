package com.yxkj.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yxkj.dao.OrderItemDao;
import com.yxkj.dao.RefundHistoryDao;
import com.yxkj.entity.Order;
import com.yxkj.entity.OrderItem;
import com.yxkj.entity.RefundHistory;
import com.yxkj.entity.commonenum.CommonEnum.RefundStatus;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.service.OrderItemService;
import com.yxkj.utils.PayUtil;
import com.yxkj.utils.SnowflakeIdWorker;

@Service("orderItemServiceImpl")
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItem, Long> implements
    OrderItemService {

  @Resource(name = "orderItemDaoImpl")
  private OrderItemDao orderItemDao;

  @Resource(name = "refundHistoryDaoImpl")
  private RefundHistoryDao refundHistoryDao;

  @Resource(name = "orderItemDaoImpl")
  public void setBaseDao(OrderItemDao orderItemDao) {
    super.setBaseDao(orderItemDao);
  }


  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public void refund(OrderItem refundOrderItem) {
    double refundFeeDouble = refundOrderItem.getPrice().doubleValue();
    Order order = refundOrderItem.getUserOrder();
    boolean refundResponse = false;
    RefundHistory history = new RefundHistory();
    history.setRefundOrder(order);
    history.setPaymentType(order.getPaymentType());
    history.setRefundAmount(String.valueOf(refundFeeDouble));
    history.setRefundSn(String.valueOf(SnowflakeIdWorker.nextId(1, 1)));
    if ("alipay".equals(order.getPaymentType())) {
      refundResponse = PayUtil.aliRefund(order.getSn(), String.valueOf(refundFeeDouble));
    } else if ("wx".equals(order.getPaymentType())) {
      String totalFee =
          String.valueOf((order.getAmount().multiply(new BigDecimal(100)).intValue()));
      String refundFee = String.valueOf((int) (refundFeeDouble * 100));
      refundResponse = PayUtil.weRefund(order.getSn(), totalFee, refundFee, history.getRefundSn());
    }

    refundHistoryDao.persist(history);
    if (refundResponse) {
      // 退款成功，更新退款状态
      refundOrderItem.setRefundStatus(RefundStatus.REFUNDED);
      orderItemDao.merge(refundOrderItem);
    }

  }
}
