package com.yxkj.dao.impl; 

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.SupplementList;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.SupplementListDao;

@Repository("supplementListDaoImpl")
public class SupplementListDaoImpl extends  BaseDaoImpl<SupplementList,Long> implements SupplementListDao {

  @SuppressWarnings("unchecked")
  @Override
  public List<Object> findWaitSupplyCountByCntrId(Long cntrId) {
    String jpql = "SELECT s.waitSupplyCount FROM SupplementList s WHERE s.cntrId = :cntrId";
    Query query = entityManager.createQuery(jpql).setParameter("cntrId", cntrId);
    
    return query.getResultList();
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List<Object> findWaitSupplyCountBySuppId(Long suppId) {
    String jpql = "SELECT s.waitSupplyCount FROM SupplementList s WHERE s.suppId = :suppId";
    Query query = entityManager.createQuery(jpql).setParameter("suppId", suppId);
    
    return (List<Object>) query.getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> findWaitSupplySceneList(Long suppId) {
    String jpql = "SELECT DISTINCT s.sceneSn, s.sceneName FROM SupplementList s WHERE s.suppId = :suppId";
    Query query = entityManager.createQuery(jpql).setParameter("suppId", suppId);
    return query.getResultList();
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> findWaitSupplyGoodsCategoryList(Long suppId) {
    String jpql = "SELECT DISTINCT g.category.id, g.category.cateName FROM Goods g WHERE g.sn IN "
        + "(SELECT s.goodsSn FROM SupplementList s WHERE s.suppId = :suppId)";
    Query query = entityManager.createQuery(jpql).setParameter("suppId", suppId);
    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> findWaitSupplyGoodsList(Long suppId, String sceneSn, Long cateId,
      int pageNo, int pageSize) {
    String jpql = "SELECT s.goodsSn, s.goodsName, sum(s.waitSupplyCount)"
        + " FROM SupplementList s WHERE s.suppId = :suppId";
    if(sceneSn != null && !"".equals(sceneSn))
      jpql += " AND s.sceneSn = :sceneSn";
    if(cateId != null && cateId != 0)
      jpql += " AND s.goodsSn IN (SELECT g.sn FROM Goods g WHERE g.category.id = :cateId)";
    jpql += " GROUP BY s.goodsSn";
    Query query = entityManager.createQuery(jpql);
    query.setParameter("suppId", suppId);
    if(sceneSn != null && !"".equals(sceneSn))
      query.setParameter("sceneSn", sceneSn);
    if(cateId != null && cateId != 0)
      query.setParameter("cateId", cateId);
    query.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize);
    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> findWaitSupplyGoodsDetails(Long suppId, String goodsSn) {
    String jpql = "SELECT s.goodsName, s.sceneName, s.waitSupplyCount FROM SupplementList s WHERE s.suppId = :suppId AND s.goodsSn = :goodsSn";
    Query query = entityManager.createQuery(jpql).setParameter("suppId", suppId).setParameter("goodsSn", goodsSn);
    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> getWaitSupplyContainerGoods(Long suppId, Long cntrId, int pageNo,
      int pageSize) {
    String sql = "SELECT s.id, s.goods_sn, s.goods_name, c.sn, s.wait_supply_count, s.remain_count FROM t_supp_list s JOIN t_cntr_channel c"
        + " ON s.channel = c.id WHERE s.supp_id = :suppId AND s.cntr_id = :cntrId";
    Query query =entityManager.createNativeQuery(sql).setParameter("suppId", suppId).setParameter("cntrId", cntrId)
        .setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize);
    return query.getResultList();
  }

  
  @SuppressWarnings("unchecked")
  @Override
  public List<Object> findWaitSupplyCountSceneSn(String sceneSn) {
    String jpql = "SELECT s.waitSupplyCount FROM SupplementList s WHERE s.sceneSn = :sceneSn";
    Query query = entityManager.createQuery(jpql).setParameter("sceneSn", sceneSn);
    
    return query.getResultList();
  }

}