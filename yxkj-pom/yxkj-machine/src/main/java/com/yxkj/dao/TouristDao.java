package com.yxkj.dao;

import com.yxkj.entity.Tourist;
import com.yxkj.framework.dao.BaseDao;

public interface TouristDao extends BaseDao<Tourist, Long> {


  /**
   * 根据游客唯一标识查询游客
   * 
   * @param userId
   * @return
   */
  Tourist getByUserId(String userId);

  /**
   * 获取微信公众号accessToken
   * 
   * @param accessToken
   * @return
   */
  String getJsApiTicket(String accessToken);

  /**
   * 获取微信公众号jsapi ticket
   * 
   * @param appId
   * @param appSecret
   * @return
   */
  String getAccessToken(String appId, String appSecret);
}
