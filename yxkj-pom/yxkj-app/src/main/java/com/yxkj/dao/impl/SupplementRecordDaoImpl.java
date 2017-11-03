package com.yxkj.dao.impl; 

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.SupplementRecord;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.SupplementRecordDao;

@Repository("supplementRecordDaoImpl")
public class SupplementRecordDaoImpl extends  BaseDaoImpl<SupplementRecord,Long> implements SupplementRecordDao {

  @SuppressWarnings("unchecked")
  @Override
  public List<SupplementRecord> findRecordByCntrId(Long suppId, Long cntrId) {
    String jpql = "FROM SupplementRecord s WHERE s.suppId = :suppId AND s.cntrId = :cntrId";
    Query query = entityManager.createQuery(jpql).setParameter("cntrId", cntrId).setParameter("suppId", suppId);
    try {
      return query.getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> findSupplementVendingContainerBySceneSn(Long suppId, String sceneSn) {
    String jpql = "SELECT DISTINCT s.cntrId, s.cntrSn FROM SupplementRecord s WHERE s.suppId = :suppId AND s.sceneSn = :sceneSn";
    Query query = entityManager.createQuery(jpql).setParameter("suppId", suppId).setParameter("sceneSn", sceneSn);
    try {
      return query.getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> findSupplementGoodsRecordByCntrId(Long suppId, Long cntrId) {
    String jpql = "SELECT s.channel.sn, s.goodsSn, s.goodsName, s.waitSupplyCount, s.supplyCount FROM SupplementRecord s WHERE s.suppId = :suppId AND s.cntrId = :cntrId";
    Query query = entityManager.createQuery(jpql).setParameter("cntrId", cntrId).setParameter("suppId", suppId);
    try {
      return query.getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }
  
}