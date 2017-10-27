package com.yxkj.dao.impl; 

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.Scene;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.SceneDao;
@Repository("sceneDaoImpl")
public class SceneDaoImpl extends  BaseDaoImpl<Scene,Long> implements SceneDao {

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> findWaitSupplyScene(Long suppId, int pageNo, int pageSize) {
    String jpql = "SELECT s.sn, s.name FROM Scene s WHERE s.cntrKeeper.id = :suppId";
    Query query = entityManager.createQuery(jpql).setParameter("suppId", suppId)
        .setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize);
    
    return query.getResultList();
  }

  @Override
  public Object findSceneNameBySn(String sn) {
    String jpql = "SELECT s.name FROM Scene s WHERE s.sn = :sn";
    Query query = entityManager.createQuery(jpql).setParameter("sn", sn);
    
    return query.getSingleResult();
  }
  
}