package com.yxkj.dao.impl; 

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ContainerChannel;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.ContainerChannelDao;
@Repository("containerChannelDaoImpl")
public class ContainerChannelDaoImpl extends  BaseDaoImpl<ContainerChannel,Long> implements ContainerChannelDao {

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> getContainerGoodsList(Long cntrId, int pageNo, int pageSize) {
    String jpql = "SELECT c.id, c.sn, c.goods.sn, c.goods.name , c.surplus, (c.capacity-c.surplus)"
        + " FROM ContainerChannel c WHERE c.cntr.id = :cntrId";
    Query query = entityManager.createQuery(jpql).setParameter("cntrId", cntrId)
        .setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize);
    try {
      return query.getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> getChannelSupplyInfo() {
    String jpql = "SELECT c.id, c.cntr.id, c.cntr.sn, c.cntr.keeperId, c.goods.sn, c.goods.name, c.cntr.scene.id, c.cntr.scene.sn,"
        + " c.cntr.scene.name, c.surplus, (c.capacity-c.surplus) FROM ContainerChannel c WHERE c.surplus < c.capacity";
    Query query = entityManager.createQuery(jpql);
    try {
      return query.getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }
  
}