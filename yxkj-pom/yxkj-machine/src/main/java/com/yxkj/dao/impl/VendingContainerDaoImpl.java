package com.yxkj.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.yxkj.dao.VendingContainerDao;
import com.yxkj.entity.VendingContainer;
import com.yxkj.framework.dao.impl.BaseDaoImpl;

@Repository("vendingContainerDaoImpl")
public class VendingContainerDaoImpl extends BaseDaoImpl<VendingContainer, Long> implements
    VendingContainerDao {

  @Override
  public VendingContainer getByImei(String imei) {
    if (imei == null) {
      return null;
    }
    try {
      String jpql = "select vc from VendingContainer where vc.sn = :imei";
      return entityManager.createQuery(jpql, VendingContainer.class)
          .setFlushMode(FlushModeType.COMMIT).setParameter("imei", imei).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

}
