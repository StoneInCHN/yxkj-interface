package com.yxkj.dao.impl; 

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.GoodsPic;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.GoodsPicDao;
@Repository("goodsPicDaoImpl")
public class GoodsPicDaoImpl extends  BaseDaoImpl<GoodsPic,Long> implements GoodsPicDao {

  @Override
  public Object findGoodsPicByGoodsSn(String goodsSn) {
    String sql = "SELECT p.source FROM t_goods_image p WHERE p.orders = 0 AND p.goods = (SELECT g.id FROM t_goods g WHERE g.sn = :goodsSn)";
    Query query = entityManager.createNativeQuery(sql).setParameter("goodsSn", goodsSn);
    try {
      return query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

}