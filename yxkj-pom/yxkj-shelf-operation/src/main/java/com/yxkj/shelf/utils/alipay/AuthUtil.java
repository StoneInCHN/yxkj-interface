package com.yxkj.shelf.utils.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;


/**
 *
 * 支付宝授权获取用户信息工具类
 * 
 * @author Andrea
 * @version 创建时间：2017年9月27日 下午3:20:27
 * 
 */
public class AuthUtil {

  /**
   * 获取授权token
   * 
   * @param publicKey
   * @param privateKey
   * @param appId
   * @param authCode
   */
  public static String alipay_getToken(String publicKey, String privateKey, String appId,
      String authCode) {

    AlipayClient alipayClient =
        new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, privateKey, "json",
            "UTF-8", publicKey, "RSA");
    AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
    request.setCode(authCode);
    request.setGrantType("authorization_code");
    try {
      AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
      // System.out.println(oauthTokenResponse.getBody());
      return oauthTokenResponse.getAccessToken();
    } catch (AlipayApiException e) {
      // 处理异常
      e.printStackTrace();
      return null;
    }
  }


  public static String alipay_getUserInfo(String publicKey, String privateKey, String appId,
      String auth_token) {
    AlipayClient alipayClient =
        new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, privateKey, "json",
            "UTF-8", publicKey, "RSA");
    AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
    // String auth_token = "authusrBf77656ae996e46d48fff46755442aC11";
    try {
      AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(request, auth_token);
      // System.out.println(userinfoShareResponse.getBody());
      return userinfoShareResponse.getBody();
    } catch (AlipayApiException e) {
      // 处理异常
      e.printStackTrace();
      return null;
    }
  }
}
