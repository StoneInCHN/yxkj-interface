package com.yxkj.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.yxkj.commonenum.CommonEnum;
import org.springframework.stereotype.Repository;

import com.yxkj.dao.OrderDao;
import com.yxkj.entity.CmdMsg;
import com.yxkj.entity.Order;
import com.yxkj.framework.dao.impl.BaseDaoImpl;

@Repository("orderDaoImpl")
public class OrderDaoImpl extends BaseDaoImpl<Order, Long> implements OrderDao {
  /**
   * 根据orderID 查询需要出货的数据信息
   *
   * @param orderId
   * @return
   */
  @Override
  public List<CmdMsg> salesOut(Long orderId) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(
        "select orderItem.id, container.sn as csn, category.cntr_type,channel.sn as cn,tOrder.device_no   ");
    stringBuffer.append(
        " from t_order tOrder, t_order_item orderItem, t_cntr_channel channel,t_vending_container container ,t_cntr_category category");
    stringBuffer.append(
        " where orderItem.cntr_id = container.id and orderItem.channel_sn = channel.sn  and orderItem.user_order = tOrder.id and container.category =category.id ");
    stringBuffer.append(" and tOrder.id = ");
    stringBuffer.append(orderId);
    Query query =
        entityManager.createNativeQuery(stringBuffer.toString()).setFlushMode(FlushModeType.COMMIT);
    List<CmdMsg> cmdMsgList = new ArrayList<>();
    List<Object[]> resultList = query.getResultList();

    resultList.forEach(array -> {
      Map<String, String> contentMap = new HashMap<>();
      contentMap.put("orderItemId", String.valueOf(array[0]));
      CmdMsg cmdMsg = new CmdMsg();
      cmdMsg.setContent(contentMap);
      cmdMsg.setAddress(String.valueOf(array[1]));
      cmdMsg.setType(CommonEnum.CmdType.values()[(int) array[2]]);
      String channelSn = String.valueOf(array[3]);
      cmdMsg.setBox(Integer.parseInt(channelSn.substring(1, channelSn.length())));
      cmdMsg.setDeviceNo((String) array[4]);
      cmdMsgList.add(cmdMsg);
    });

    return cmdMsgList;
  }

  @Override
  public Order getOrderBySn(String orderSn) {
    if (orderSn == null) {
      return null;
    }
    try {
      String jpql = "select o from Order o where o.sn = :sn";
      return entityManager.createQuery(jpql, Order.class).setFlushMode(FlushModeType.COMMIT)
          .setParameter("sn", orderSn).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }



}
