package com.yxkj.json.request;

import com.yxkj.json.base.BaseRequest;

/**
 *
 * 用户req
 * 
 * @author Andrea
 * @version 创建时间：2017年9月26日 下午6:21:14
 * 
 */
public class UserInfoReq extends BaseRequest {

  /**
   * 支付宝或微信authCode
   */
  private String authCode;

  /**
   * 商品集合字符串
   */
  private String gList;

  /**
   * 扫码类型
   */
  private String type;

  /**
   * 中控imei号
   */
  private String imei;

  /**
   * 当前页面url地址
   */
  private String curUrl;


  public String getCurUrl() {
    return curUrl;
  }

  public void setCurUrl(String curUrl) {
    this.curUrl = curUrl;
  }

  public String getImei() {
    return imei;
  }

  public void setImei(String imei) {
    this.imei = imei;
  }

  public String getgList() {
    return gList;
  }

  public void setgList(String gList) {
    this.gList = gList;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getAuthCode() {
    return authCode;
  }

  public void setAuthCode(String authCode) {
    this.authCode = authCode;
  }



}
