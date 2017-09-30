package com.yxkj.shelf.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxkj.entity.Company;
import com.yxkj.entity.Tourist;
import com.yxkj.entity.commonenum.CommonEnum.DeviceType;
import com.yxkj.entity.commonenum.CommonEnum.UserChannel;
import com.yxkj.shelf.common.log.LogUtil;
import com.yxkj.shelf.dao.TouristDao;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;
import com.yxkj.shelf.service.TouristService;
import com.yxkj.shelf.utils.ApiUtils;
import com.yxkj.shelf.utils.alipay.AuthUtil;
import com.yxkj.shelf.utils.wxpay.WeixinUtil;

@Service("touristServiceImpl")
public class TouristServiceImpl extends BaseServiceImpl<Tourist, Long> implements TouristService {

  @Resource(name = "touristDaoImpl")
  private TouristDao touristDao;

  @Resource(name = "touristDaoImpl")
  public void setBaseDao(TouristDao touristDao) {
    super.setBaseDao(touristDao);
  }

  @Override
  public Map<String, Object> getWxUserInfo(String appId, String appSec, String authCode) {
    try {
      Map<String, Object> res = new HashMap<String, Object>();
      /**
       * 根据authcode获取access_token
       */
      String url =
          "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appSec
              + "&code=" + authCode + "&grant_type=authorization_code";
      String auth_token = ApiUtils.get(url);
      // LogUtil.debug(this.getClass(), "getWxUserInfo", "get access token param: %s, result: %s",
      // url,
      // auth_token);
      ObjectMapper objectMapper = new ObjectMapper();
      Map<String, Object> accessTokenMap = objectMapper.readValue(auth_token, Map.class);
      // 返回结果
      // {"access_token":"HCTwb7ONpje1klLQovppg9LJgDONKMrr1dk_ISVzyVR8f4XZPuhtOqbYKR0fm3SBGmH-3Ow1CZpqfzBGvDrLMg","expires_in":7200,"refresh_token":"91Y7stEMGu0oTMlebouGZAI0libMzpWMaK19DCjF4vL1ByXxxYmOYO9JknQyIGn4cwNTAUvfqhYKjS19CPwGng","openid":"od-PO1Z6CFNiRUVGdPDyfpa-10Ok","scope":"snsapi_userinfo","unionid":"oJjRO00firo-TEHDNNiP6xghHJVc"}
      /**
       * 根据access_token获取openid,昵称等信息
       */
      url =
          "https://api.weixin.qq.com/sns/userinfo?access_token="
              + accessTokenMap.get("access_token") + "&openid=" + accessTokenMap.get("openid")
              + "&lang=zh_CN";
      String user_info = ApiUtils.get(url);
      Map<String, Object> userInfoMap = objectMapper.readValue(user_info, Map.class);
      // {"openid":"od-PO1Z6CFNiRUVGdPDyfpa-10Ok","nickname":"andrea","sex":1,"language":"zh_CN","city":"鎴愰兘","province":"鍥涘窛","country":"涓浗","headimgurl":"http:\/\/wx.qlogo.cn\/mmopen\/vi_32\/cfqG2rQC876fb9my7xlINKfzVw2T9iaS7Pl8DmdcX5DWbPsworuMPLsthHMgN9AlomroH81mFqaGS58m1RVwicibA\/0","privilege":[],"unionid":"oJjRO00firo-TEHDNNiP6xghHJVc"}
      LogUtil.debug(this.getClass(), "getWxUserInfo", "result: %s", user_info);
      if (userInfoMap.get("openid") != null) {
        res.put("userId", userInfoMap.get("openid"));
        res.put("nickname", userInfoMap.get("nickname"));
        // res.put("sex", userInfoMap.get("sex"));
        return res;
      }

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

    return null;
  }

  @Override
  public Map<String, Object> getAlipayUserInfo(String publicKey, String privateKey, String appId,
      String authCode) {
    Map<String, Object> res = new HashMap<String, Object>();
    try {
      String access_token = AuthUtil.alipay_getToken(publicKey, privateKey, appId, authCode);
      if (!StringUtils.isEmpty(access_token)) {
        String userInfo = AuthUtil.alipay_getUserInfo(publicKey, privateKey, appId, access_token);
        LogUtil.debug(this.getClass(), "getAlipayUserInfo", "result: %s", userInfo);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> userInfoRes = objectMapper.readValue(userInfo, Map.class);
        Map<String, Object> userInfoMap =
            (Map<String, Object>) userInfoRes.get("alipay_user_info_share_response");

        String resCode = (String) userInfoMap.get("code");
        if ("10000".equals(resCode)) {
          res.put("userId", userInfoMap.get("user_id"));
          res.put("nickname", userInfoMap.get("nick_name"));
          return res;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }


  @Override
  public Tourist saveTourist(String userId, String nickName, String type, Company company) {
    Tourist t = getByUserId(userId);
    if (t == null) {
      t = new Tourist();
      t.setUserName(userId);
      if ("wx".equals(type)) {
        t.setUserChannel(UserChannel.WECHAT);
      }
      if ("alipay".equals(type)) {
        t.setUserChannel(UserChannel.ALIPAY);
      }
      t.setNickName(nickName);
      t.setDeviceType(DeviceType.SHELF);
      t.setCompanyId(company.getId());
      t.setCompanyName(company.getDisplayName());
      touristDao.persist(t);
    }
    return t;
  }


  @Override
  public Tourist getByUserId(String userId) {
    return touristDao.getByUserId(userId);
  }

  @Override
  public Map<String, Object> getJsapiConfig(String curUrl, String appId, String appSecret) {
    String access_token = touristDao.getAccessToken(appId, appSecret);
    String jsapi_ticket = touristDao.getJsApiTicket(access_token);
    Map<String, Object> map = new HashMap<String, Object>();
    map.putAll(WeixinUtil.jsApiSign(jsapi_ticket, curUrl));
    return map;
  }


}
