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
  public List<Object[]> findWaitSupplyScene(Long suppId, int pageNo, int pageSize) {
    String jpql = "SELECT s.sn, s.name FROM Scene s WHERE s.cntrKeeper.id = :suppId";
    Query query = entityManager.createQuery(jpql).setParameter("suppId", suppId)
        .setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize);
    
    return query.getResultList();
  }

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
  public Integer findWaitSupplyCount(Long cntrId) {
    String jpql = "SELECT s.waitSupplyCount FROM SupplementList s WHERE s.cntrId = :cntrId";
    Query query = entityManager.createQuery(jpql).setParameter("cntrId", cntrId);
    
    return (Integer) query.getSingleResult();
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
    String jpql = "SELECT s.goodsSn, s.goodsName, s.waitSupplyCount FROM SupplementList s WHERE s.suppId = :suppId";
    if(sceneSn!=null && !"".equals(sceneSn))
      jpql += " AND s.sceneSn = :sceneSn";
    if(cateId!=null)
      jpql += " AND s.goodsSn IN (SELECT g.sn FROM Goods g WHERE g.category.id = :cateId)";
    Query query = entityManager.createQuery(jpql);
    query.setParameter("suppId", suppId);
    if(sceneSn!=null && !"".equals(sceneSn))
      query.setParameter("sceneSn", sceneSn);
    if(cateId!=null)
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

  @Override
  public Object findGoodsPicByGoodsSn(String goodsSn) {
    String sql = "SELECT p.source FROM t_goods_image p WHERE p.orders = 0 AND p.goods = (SELECT g.id FROM t_goods g WHERE g.sn = :goodsSn)";
    Query query = entityManager.createNativeQuery(sql).setParameter("goodsSn", goodsSn);
    return query.getSingleResult();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> getWaitSupplyContainerGoods(Long suppId, Long cntrId, int pageNo,
      int pageSize) {
    String sql = "SELECT s.goods_sn, s.goods_name, c.sn, s.wait_supply_count FROM t_supp_list s JOIN t_cntr_channel c"
        + " ON s.channel = c.id WHERE s.supp_id = :suppId AND s.cntr_id = :cntrId";
    Query query =entityManager.createNativeQuery(sql).setParameter("suppId", suppId).setParameter("cntrId", cntrId)
        .setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize);
    return query.getResultList();
  }

}