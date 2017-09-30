package com.yxkj.shelf.service;

import java.util.Map;

import com.yxkj.entity.Company;
import com.yxkj.entity.Tourist;
import com.yxkj.shelf.framework.service.BaseService;

public interface TouristService extends BaseService<Tourist, Long> {

  /**
   * 获取微信用户信息
   * 
   * @param appId
   * @param appSec
   * @param authCode
   * @return
   */
  Map<String, Object> getWxUserInfo(String appId, String appSec, String authCode);


  /**
   * 获取支付宝用户信息
   * 
   * @param publicKey
   * @param privateKey
   * @param appId
   * @param authCode
   * @return
   */
  Map<String, Object> getAlipayUserInfo(String publicKey, String privateKey, String appId,
      String authCode);

  /**
   * 保存游客信息
   * 
   * @param userId
   * @param nickName
   * @param type
   * @return
   */
  Tourist saveTourist(String userId, String nickName, String type, Company company);

  /**
   * 根据游客唯一标识查询游客
   * 
   * @param userId
   * @return
   */
  Tourist getByUserId(String userId);

  /**
   * 获取微信jsapi参数
   * 
   * @param curUrl
   * @return
   */
  Map<String, Object> getJsapiConfig(String curUrl, String appId, String appSecret);

}
