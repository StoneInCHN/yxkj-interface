package com.yxkj.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.yxkj.common.commonenum.CommonEnum;
import com.yxkj.common.entity.CmdMsg;
import com.yxkj.dao.*;
import com.yxkj.entity.*;
import com.yxkj.service.OrderItemTmpService;
import com.yxkj.service.RefundHistoryService;
import com.yxkj.utils.PayUtil;
import com.yxkj.utils.SnowflakeIdWorker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yxkj.common.log.LogUtil;
import com.yxkj.entity.commonenum.CommonEnum.OrderStatus;
import com.yxkj.entity.commonenum.CommonEnum.PickupStatus;
import com.yxkj.entity.commonenum.CommonEnum.PurMethod;
import com.yxkj.entity.commonenum.CommonEnum.ShipmentStatus;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.beans.GoodsBean;
import com.yxkj.service.CmdService;
import com.yxkj.service.OrderService;

import static com.yxkj.entity.commonenum.CommonEnum.PickupStatus.LACK;
import static com.yxkj.entity.commonenum.CommonEnum.RefundStatus.NOT_REFUND;

@Service("orderServiceImpl")
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {
  @Resource(name = "orderDaoImpl")
  private OrderDao orderDao;

  @Resource(name = "touristDaoImpl")
  private TouristDao touristDao;

  @Resource(name = "sceneDaoImpl")
  private SceneDao sceneDao;

  @Resource(name = "cmdServiceImpl")
  private CmdService cmdService;

  @Resource(name = "orderItemTmpServiceImpl")
  private OrderItemTmpService orderItemTmpService;
  @Resource(name = "containerChannelDaoImpl")
  private ContainerChannelDao containerChannelDao;

  @Resource(name = "refundHistoryServiceImpl")
  private RefundHistoryService refundHistoryService;

  @Resource(name = "orderDaoImpl")
  public void setBaseDao(OrderDao orderDao) {
    super.setBaseDao(orderDao);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public Order createCntrOrder(String payType, String userName, String imei,
      List<GoodsBean> goodsBeans) {
    Order order = new Order();
    order.setPaymentType(payType);
    order.setPaymentTypeId(payType);
    order.setDeviceNo(imei);
    order.setStatus(OrderStatus.UNPAID);
    order.setPurMethod(PurMethod.CONTROLL_MACHINE);
    Scene scene = sceneDao.getByImei(imei);
    order.setSceneId(scene.getId());
    order.setSceneName(scene.getName());
    Tourist t = touristDao.getByUserId(userName);
    order.setTourist(t);
    int totalCount = 0;
    BigDecimal profit = new BigDecimal(0);
    BigDecimal amount = new BigDecimal(0);

    for (GoodsBean goodsBean : goodsBeans) {
      ContainerChannel cc = goodsBean.getChannel();
      Goods gs = cc.getGoods();
      VendingContainer vc = cc.getCntr();
      totalCount += goodsBean.getCount();
      for (int i = 0; i < goodsBean.getCount(); i++) {
        OrderItem item = new OrderItem();
        item.setUserOrder(order);
        item.setgName(gs.getName());
        item.setSpec(gs.getSpec());
        item.setPrice(cc.getPrice());
        item.setgSn(gs.getSn());
        item.setChannelSn(cc.getSn());
        item.setCostPrice(gs.getCostPrice());

        // 货柜信息
        item.setCntrId(vc.getId());
        item.setCntrSn(vc.getSn());
        // 货道信息
        item.setChannelSn(cc.getSn());

        item.setPickupStatus(PickupStatus.WAIT_PICKUP);
        item.setShipmentStatus(ShipmentStatus.NOT_SHIPMENT);
        // 计算订单总价
        amount = amount.add(item.getPrice());
        // 计算订单毛利
        BigDecimal itemProfit = cc.getPrice().subtract(gs.getCostPrice());
        profit = profit.add(itemProfit);
        order.getOrderItems().add(item);

        // 库存锁定
        cc.setOfflineLocalLock(cc.getOfflineLocalLock() + 1);
        containerChannelDao.merge(cc);
      }
    }
    order.setAmount(amount);
    order.setItemCount(totalCount);
    order.setProfit(profit);
    order.setSn(String.valueOf(SnowflakeIdWorker.nextId(0, 0)));
    orderDao.persist(order);
    return order;
  }


  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public synchronized Order callbackAfterPay(String orderSn) {
    Order order = orderDao.getOrderBySn(orderSn);
    if (!OrderStatus.UNPAID.equals(order.getStatus())) {
      LogUtil.debug(this.getClass(), "callbackAfterPay",
          "This order already deal with. orderSn: %s, orderStatus: %s", order.getSn(),
          order.getStatus().toString());
      return order;
    }
    order.setStatus(OrderStatus.PAID);
    order.setPaymentTime(new Date());

    Set<OrderItem> orderItemSet = order.getOrderItems();

    List<OrderItem> refundOrderItemList = new ArrayList<>();
    // 支付完成，更新库存数据，
    for (OrderItem orderItem : orderItemSet) {
      ContainerChannel containerChannel = containerChannelDao.find(orderItem.getCntrId());
      // 更新库存
      if (containerChannel.getSurplus() > 0) {
        containerChannel.setSurplus(containerChannel.getSurplus() - 1);
        orderItemTmpService.updateAfterPay(orderItem, containerChannel);

      } else {
        orderItem.setPickupStatus(LACK);
        orderItem.setRefundStatus(NOT_REFUND);
        refundOrderItemList.add(orderItem);
        LogUtil.debug(this.getClass(), "callbackAfterPay", "商品缺货:%s;货道：%s", orderItem.getgName(),
            orderItem.getChannelSn());
      }
    }
    // 如果缺货，自动退款流程
    if (!refundOrderItemList.isEmpty()) {
      refund(order, refundOrderItemList);
    }
    // 设置物业提成
    Scene scene = sceneDao.find(order.getSceneId());
    BigDecimal fenRunPoint = scene.getFenRunPoint();
    if (fenRunPoint != null) {
      order.setFenRunPoint(fenRunPoint);
      order.setFenRunAmount(fenRunPoint.multiply(order.getAmount()));
    }
    orderDao.merge(order);
    cmdService.notificationCmd(order.getDeviceNo(), CommonEnum.CmdType.PAYMENT_SUCCESS);
    cmdService.salesOut(order.getId());
    LogUtil.debug(this.getClass(), "callbackAfterPay",
        "update cntr order info finished for pay callback. orderId: %s,sn: %s,orderStatus: %s,payTime: %s",
        order.getId(), order.getSn(), order.getStatus().toString(), order.getPaymentTime());
    return order;
  }

  @Override
  public Order getOrderBySn(String orderSn) {
    return orderDao.getOrderBySn(orderSn);
  }

  /**
   * 根据orderID发送出货请求
   * 
   * @param orderId
   */
  @Override
  public List<CmdMsg> salesOut(Long orderId) {

    return orderDao.salesOut(orderId);
  }

  /**
   * 退款
   * 
   * @param refundOrderItemList
   */
  // @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void refund(Order order, List<OrderItem> refundOrderItemList) {
    double refundFeeDouble = 0;
    for (OrderItem orderItem : refundOrderItemList) {
      refundFeeDouble += orderItem.getPrice().doubleValue();
    }
    String orderSn = order.getSn();
    boolean refundResponse = false;
    RefundHistory history = new RefundHistory();
    history.setRefundOrder(order);
    history.setPaymentType(order.getPaymentType());
    history.setRefundAmount(String.valueOf(refundFeeDouble));
    history.setRefundSn(String.valueOf(SnowflakeIdWorker.nextId(1, 1)));

    if ("alipay".equals(order.getPaymentType())) {
      refundResponse = PayUtil.aliRefund(orderSn, String.valueOf(refundFeeDouble));
    } else if ("wx".equals(order.getPaymentType())) {
      String totalFee =
          String.valueOf((order.getAmount().multiply(new BigDecimal(100)).intValue()));
      String refundFee = String.valueOf((int) (refundFeeDouble * 100));
      refundResponse = PayUtil.weRefund(orderSn, totalFee, refundFee, history.getRefundSn());
    }

    refundHistoryService.save(history);
    if (refundResponse) {
      // 退款成功，更新退款状态
      for (OrderItem orderItem : refundOrderItemList) {
        orderItem.setRefundStatus(com.yxkj.entity.commonenum.CommonEnum.RefundStatus.REFUNDED);
      }
    }
  }
}
