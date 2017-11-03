package com.yxkj.dao.impl;

import com.yxkj.entity.OrderItemTmp;
import org.springframework.stereotype.Repository;

import com.yxkj.dao.OrderItemTmpDao;
import com.yxkj.framework.dao.impl.BaseDaoImpl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

@Repository("orderItemTmpDaoImpl")
public class OrderItemTmpDaoImpl extends BaseDaoImpl<OrderItemTmp, Long>
    implements OrderItemTmpDao {

  @Override
  public OrderItemTmp getOrderItemTmpByItemId(Long orderItemId) {
			if (orderItemId == null) {
					return null;
			}
			try {
					String jpql = "select tmp from OrderItemTmp tmp where tmp.orderItemId = :orderItemId";
					return entityManager.createQuery(jpql, OrderItemTmp.class).setFlushMode(FlushModeType.COMMIT).setParameter("orderItemId", orderItemId)
							.getSingleResult();
			} catch (NoResultException e) {
					return null;
			}
	}
}
