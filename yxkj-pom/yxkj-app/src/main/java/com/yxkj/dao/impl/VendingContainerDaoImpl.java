package com.yxkj.dao.impl; 

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.VendingContainer;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.VendingContainerDao;
@Repository("vendingContainerDaoImpl")
public class VendingContainerDaoImpl extends  BaseDaoImpl<VendingContainer,Long> implements VendingContainerDao {
  
  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> findCentralVendingContainer(String sceneSn) {
    String sql = "SELECT v.id, v.sn FROM t_vending_container v WHERE v.parent is null AND v.scene = "
        + "(SELECT s.id FROM t_scene s WHERE s.sn = :sceneSn)";
    Query query = entityManager.createNativeQuery(sql).setParameter("sceneSn", sceneSn);
    return query.getResultList();
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> findChildrenVendingContainer(Long id){
    String sql = "SELECT v.id, v.sn FROM t_vending_container v WHERE v.parent = :id";
    Query query = entityManager.createNativeQuery(sql).setParameter("id", id);
    
    return query.getResultList();
  }
  @Override
  public VendingContainer getByImei(String imei) {
    if (imei == null) {
      return null;
    }
    try {
      String jpql = "select vc from VendingContainer vc where vc.sn = :imei";
      return entityManager.createQuery(jpql, VendingContainer.class)
          .setFlushMode(FlushModeType.COMMIT).setParameter("imei", imei).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }
}
