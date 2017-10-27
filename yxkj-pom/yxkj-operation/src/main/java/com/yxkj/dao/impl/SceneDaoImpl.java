package com.yxkj.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.yxkj.dao.SceneDao;
import com.yxkj.entity.Scene;
import com.yxkj.entity.commonenum.CommonEnum.CommonStatus;
import com.yxkj.framework.dao.impl.BaseDaoImpl;

@Repository("sceneDaoImpl")
public class SceneDaoImpl extends BaseDaoImpl<Scene, Long> implements SceneDao {

  @Override
  public List<Scene> getByKey(String key) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Scene> criteriaQuery = criteriaBuilder.createQuery(Scene.class);
    Root<Scene> scene = criteriaQuery.from(Scene.class);
    Predicate restrictions =
        criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder
            .conjunction();
    restrictions =
                criteriaBuilder.and(restrictions, criteriaBuilder.equal(scene.get("removeStatus"), CommonStatus.ACITVE));
    restrictions =
        criteriaBuilder.and(restrictions, criteriaBuilder.like(scene.get("sn"), "%" + key + "%"));
    restrictions =
        criteriaBuilder.or(restrictions, criteriaBuilder.like(scene.get("name"), "%" + key + "%"));
    criteriaQuery.where(restrictions);
    TypedQuery<Scene> query =
        entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
    return query.getResultList();
  }

}
