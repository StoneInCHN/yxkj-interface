package com.yxkj.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.yxkj.dao.SceneDao;
import com.yxkj.entity.Scene;
import com.yxkj.framework.dao.impl.BaseDaoImpl;

@Repository("sceneDaoImpl")
public class SceneDaoImpl extends BaseDaoImpl<Scene, Long> implements SceneDao {

  @Override
  public Scene getByImei(String imei) {
    if (imei == null) {
      return null;
    }
    try {
      String jpql =
          "select scene from Scene scene join scene.vendingContainer vc where vc.sn = :imei";
      return entityManager.createQuery(jpql, Scene.class).setFlushMode(FlushModeType.COMMIT)
          .setParameter("imei", imei).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

}
