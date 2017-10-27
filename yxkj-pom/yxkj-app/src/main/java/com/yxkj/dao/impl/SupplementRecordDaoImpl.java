package com.yxkj.dao.impl; 

import java.util.List;

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
    
    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> findSupplementVendingContainerBySceneSn(Long suppId, String sceneSn) {
    String jpql = "SELECT DISTINCT s.cntrId, s.sntrSn FROM SupplementRecord s WHERE s.suppId = :suppId AND s.sceneSn = :sceneSn";
    Query query = entityManager.createQuery(jpql).setParameter("suppId", suppId).setParameter("sceneSn", sceneSn);
    
    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> findSupplementGoodsRecordByCntrId(Long suppId, String cntrId) {
    String jpql = "SELECT s. FROM SupplementRecord s WHERE s.suppId = :suppd AND s.cntrId = :cntrId AND s.suppPic is null";
    Query query = entityManager.createQuery(jpql).setParameter("cntrId", cntrId).setParameter("suppId", suppId);
    
    return query.getResultList();
  }
  
}