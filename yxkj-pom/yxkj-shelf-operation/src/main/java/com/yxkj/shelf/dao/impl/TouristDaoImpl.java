package com.yxkj.shelf.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.yxkj.entity.Tourist;
import com.yxkj.shelf.dao.TouristDao;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;

@Repository("touristDaoImpl")
public class TouristDaoImpl extends BaseDaoImpl<Tourist, Long> implements TouristDao {


  @Override
  public Tourist getByUserId(String userId) {
    if (userId == null) {
      return null;
    }
    try {
      String jpql = "select t from Tourist t where t.userName = :userName";
      return entityManager.createQuery(jpql, Tourist.class).setFlushMode(FlushModeType.COMMIT)
          .setParameter("userName", userId).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }
}
