package com.yxkj.shelf.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.yxkj.entity.Goods;
import com.yxkj.shelf.dao.GoodsDao;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;

@Repository("goodsDaoImpl")
public class GoodsDaoImpl extends BaseDaoImpl<Goods, Long> implements GoodsDao {

  @Override
  public Goods getBySn(String sn) {
    if (sn == null) {
      return null;
    }
    try {
      String jpql = "select g from Goods g where g.sn = :sn";
      return entityManager.createQuery(jpql, Goods.class).setFlushMode(FlushModeType.COMMIT)
          .setParameter("sn", sn).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

}
