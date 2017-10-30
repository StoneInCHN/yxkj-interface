package com.yxkj.dao.impl; 

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.SupplementSumRec;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.SupplementSumRecDao;

@Repository("supplementSumRecDaoImpl")
public class SupplementSumRecDaoImpl extends  BaseDaoImpl<SupplementSumRec,Long> implements SupplementSumRecDao {
  
  @Override
  public Object[] findUnfinishedSupplyRecord(Long suppId, String sceneSn) {
    String jpql = "SELECT s.sceneSn, s.sceneName FROM SupplementSumRec s WHERE s.suppId = :suppId "
        + "AND sceneSn != :sceneSn AND s.suppEndTime is NULL";
    Query query = entityManager.createQuery(jpql).setParameter("suppId", suppId).setParameter("sceneSn", sceneSn);
    return (Object[]) query.getSingleResult();
  }
  
  @Override
  public SupplementSumRec findSupplementSumRecordBySceneSn(Long suppId, String sceneSn) {
    String jpql = "FROM SupplementSumRec s WHERE s.sceneSn = :sceneSn AND s.suppId = :suppId AND s.suppEndTime is NULL";
    Query query = entityManager.createQuery(jpql).setParameter("sceneSn", sceneSn).setParameter("suppId", suppId);
    return (SupplementSumRec) query.getSingleResult();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> findSupplementSumRecord(Long suppId, int pageNo, int pageSize) {
    String jpql = "SELECT s.sceneSn, s.sceneName, s.waitSuppTotalCount, s.suppTotalCount, s.lackCount, s.suppEndTime"
        + " FROM SupplementSumRec s"
        + " WHERE s.suppId = :suppId AND s.suppEndTime is not NULL ORDER BY s.suppEndTime ASC";
    Query query = entityManager.createQuery(jpql).setParameter("suppId", suppId)
        .setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize);
    
    return query.getResultList();
  }

}