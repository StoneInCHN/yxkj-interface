package com.yxkj.dao.impl; 

import java.util.List;

import javax.persistence.NoResultException;
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
    try {
      return (Object[]) query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }
  
  @Override
  public SupplementSumRec findSupplementSumRecordBySceneSn(Long suppId, String sceneSn) {
    String jpql = "FROM SupplementSumRec s WHERE s.sceneSn = :sceneSn AND s.suppId = :suppId AND s.suppEndTime is NULL";
    Query query = entityManager.createQuery(jpql).setParameter("sceneSn", sceneSn).setParameter("suppId", suppId);
    try {
      return (SupplementSumRec) query.getSingleResult();
    } catch (NoResultException e) {
    return null;
    }
  }

  @SuppressWarnings("unchecked")
  public List<Object> findSupplyDate(Long suppId, int pageNo, int pageSize) {
    String sql = "SELECT DISTINCT DATE_FORMAT(supp_end_time,'%m.%d')"
        + " FROM t_supp_sum_rec WHERE supp_id = :suppId AND supp_end_time is not NULL"
        + " ORDER BY supp_end_time DESC";
    Query query = entityManager.createNativeQuery(sql).setParameter("suppId", suppId)
        .setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize);
    try {
      return query.getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> findSupplementSumRecord(Long suppId, String date) {
    String sql = "SELECT scene_sn, scene_name, wait_supp_total_count, supp_total_count, DATE_FORMAT(supp_end_time,'%T')"
        + " FROM t_supp_sum_rec"
        + " WHERE supp_id = :suppId AND DATE_FORMAT(supp_end_time,'%m.%d') = :date AND supp_end_time is not NULL"
        + " ORDER BY supp_end_time DESC";
    Query query = entityManager.createNativeQuery(sql).setParameter("suppId", suppId).setParameter("date", date);
    try {
      return query.getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

}