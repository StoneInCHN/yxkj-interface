package com.yxkj.shelf.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.yxkj.entity.ShelfOrder;
import com.yxkj.shelf.dao.ShelfOrderDao;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;

@Repository("shelfOrderDaoImpl")
public class ShelfOrderDaoImpl extends BaseDaoImpl<ShelfOrder, Long> implements ShelfOrderDao {

  @Override
  public ShelfOrder getShelfOrderBySn(String orderSn) {
    if (orderSn == null) {
      return null;
    }
    try {
      String jpql = "select sOrder from ShelfOrder sOrder where sOrder.sn = :sn";
      return entityManager.createQuery(jpql, ShelfOrder.class).setFlushMode(FlushModeType.COMMIT)
          .setParameter("sn", orderSn).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

}
