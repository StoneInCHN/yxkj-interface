package com.yxkj.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.yxkj.dao.OrderDao;
import com.yxkj.entity.Order;
import com.yxkj.framework.dao.impl.BaseDaoImpl;

@Repository("orderDaoImpl")
public class OrderDaoImpl extends BaseDaoImpl<Order, Long> implements OrderDao {

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
