package com.yxkj.shelf.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.yxkj.entity.Tourist;
import com.yxkj.shelf.dao.TouristDao;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.utils.wxpay.WeixinUtil;

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


  @Cacheable(value = "jsapiTicket", key = "'jsapi_ticket'")
  public String getJsApiTicket(String accessToken) {
    return WeixinUtil.getJsapiTicket(accessToken);

  }

  @Cacheable(value = "accessToken", key = "'access_token'")
  public String getAccessToken(String appId, String appSecret) {
    return WeixinUtil.getToken(appId, appSecret);

  }
}
