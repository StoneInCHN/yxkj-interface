package com.yxkj.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yxkj.common.log.LogUtil;
import com.yxkj.dao.OrderDao;
import com.yxkj.dao.SceneDao;
import com.yxkj.dao.SnDao;
import com.yxkj.dao.TouristDao;
import com.yxkj.entity.CmdMsg;
import com.yxkj.entity.ContainerChannel;
import com.yxkj.entity.Goods;
import com.yxkj.entity.Order;
import com.yxkj.entity.OrderItem;
import com.yxkj.entity.Scene;
import com.yxkj.entity.Sn.Type;
import com.yxkj.entity.Tourist;
import com.yxkj.entity.VendingContainer;
import com.yxkj.entity.commonenum.CommonEnum.OrderStatus;
import com.yxkj.entity.commonenum.CommonEnum.PickupStatus;
import com.yxkj.entity.commonenum.CommonEnum.PurMethod;
import com.yxkj.entity.commonenum.CommonEnum.ShipmentStatus;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.beans.GoodsBean;
import com.yxkj.service.CmdService;
import com.yxkj.service.OrderService;

@Service("orderServiceImpl")
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {
  @Resource(name = "orderDaoImpl")
  private OrderDao orderDao;

  @Resource(name = "touristDaoImpl")
  private TouristDao touristDao;

  @Resource(name = "snDaoImpl")
  private SnDao snDao;

  @Resource(name = "sceneDaoImpl")
  private SceneDao sceneDao;

  @Resource(name = "cmdServiceImpl")
  private CmdService cmdService;


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
      }
    }
    order.setAmount(amount);
    order.setItemCount(totalCount);
    order.setProfit(profit);
    order.setSn(snDao.generate(Type.ORDER));
    orderDao.persist(order);
    return order;
  }


  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public Order callbackAfterPay(String orderSn) {
    Order order = orderDao.getOrderBySn(orderSn);
    if (!OrderStatus.UNPAID.equals(order.getStatus())) {
      LogUtil.debug(this.getClass(), "callbackAfterPay",
          "This order already deal with. orderSn: %s, orderStatus: %s", order.getSn(),
          order.getStatus().toString());
      return order;
    }
    order.setStatus(OrderStatus.PAID);
    order.setPaymentTime(new Date());
    orderDao.merge(order);
    salesOut(order.getId());
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
  public void salesOut(Long orderId) {

    List<CmdMsg> cmdMsgList = orderDao.salesOut(orderId);
    cmdMsgList.forEach(cmdMsg -> cmdService.sendCmd(cmdMsg));

  }
}
