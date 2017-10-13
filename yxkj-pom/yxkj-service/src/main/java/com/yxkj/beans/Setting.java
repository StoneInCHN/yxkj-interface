package com.yxkj.beans;

import java.io.Serializable;

/**
 * 系统设置
 * 
 */
public class Setting implements Serializable {

  private static final long serialVersionUID = -1478999889661796840L;



  /** 缓存名称 */
  public static final String CACHE_NAME = "setting";

  /** 缓存Key */
  public static final Integer CACHE_KEY = 0;



  /**
   * 公众平台appid
   */
  private String wxPublicAppId;


  /**
   * 授权跳转url
   */
  private String authRedirectUrl;

  /**
   * 应用APPID
   */
  private String alipayAppId;

  /**
   * 货架系统url
   */
  private String shelfSysUrl;

  /**
   * 货柜系统url
   */
  private String cntrSysUrl;


  public String getCntrSysUrl() {
    return cntrSysUrl;
  }

  public void setCntrSysUrl(String cntrSysUrl) {
    this.cntrSysUrl = cntrSysUrl;
  }

  public String getShelfSysUrl() {
    return shelfSysUrl;
  }

  public void setShelfSysUrl(String shelfSysUrl) {
    this.shelfSysUrl = shelfSysUrl;
  }

  public String getWxPublicAppId() {
    return wxPublicAppId;
  }

  public void setWxPublicAppId(String wxPublicAppId) {
    this.wxPublicAppId = wxPublicAppId;
  }


  public String getAlipayAppId() {
    return alipayAppId;
  }

  public void setAlipayAppId(String alipayAppId) {
    this.alipayAppId = alipayAppId;
  }

  public String getAuthRedirectUrl() {
    return authRedirectUrl;
  }

  public void setAuthRedirectUrl(String authRedirectUrl) {
    this.authRedirectUrl = authRedirectUrl;
  }

}
