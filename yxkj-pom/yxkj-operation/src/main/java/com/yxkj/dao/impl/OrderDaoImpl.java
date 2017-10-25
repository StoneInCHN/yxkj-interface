package com.yxkj.dao.impl;

import java.math.BigDecimal;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.yxkj.dao.OrderDao;
import com.yxkj.entity.Order;
import com.yxkj.framework.dao.impl.BaseDaoImpl;

@Repository("orderDaoImpl")
public class OrderDaoImpl extends BaseDaoImpl<Order, Long> implements OrderDao {

  @Override
  public BigDecimal getPurTotal(Long sceneId) {
    try {
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT count(distinct tourist) FROM t_order");
      if (sceneId != null) {
        sql.append(" where scene_id=");
        sql.append(sceneId);
      }
      BigDecimal total =
          new BigDecimal(entityManager.createNativeQuery(sql.toString())
              .setFlushMode(FlushModeType.COMMIT).getSingleResult().toString());
      return total;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public BigDecimal getPurRepeat(Integer count, Long sceneId) {
    try {
      StringBuffer sql = new StringBuffer();
      sql.append("select count(*) from (SELECT count(tourist) FROM t_order");
      if (sceneId != null) {
        sql.append(" where scene_id=");
        sql.append(sceneId);
      }
      sql.append(" group by tourist having count(tourist)>=");
      sql.append(count);
      sql.append(") as t");
      BigDecimal total =
          new BigDecimal(entityManager.createNativeQuery(sql.toString())
              .setFlushMode(FlushModeType.COMMIT).getSingleResult().toString());
      return total;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}
