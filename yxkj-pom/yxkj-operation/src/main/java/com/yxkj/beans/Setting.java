package com.yxkj.beans;

import java.io.Serializable;



/**
 * 系统设置
 * 
 */
public class Setting implements Serializable {

  private static final long serialVersionUID = -1478999889661796840L;

  /**
   * 验证码类型
   */
  public enum CaptchaType {

    /** 后台登录 */
    adminLogin,

    /** 租户资料上传 */
    adminApply,

    /** 找回密码 */
    findPassword,

    /** 重置密码 */
    resetPassword,

    /** 其它 */
    other
  }

  /** 验证码类型 */
  private CaptchaType[] captchaTypes;

  /** 缓存名称 */
  public static final String CACHE_NAME = "setting";

  /** 缓存Key */
  public static final Integer CACHE_KEY = 0;


  /** 密码最大长度 */
  private Integer passwordMaxlength;

  /** 密码最小长度 */
  private Integer passwordMinlength;

  /** 服务器端公钥 */
  private String serverPublicKey;

  /** 客户端公钥 */
  private String serverPrivateKey;

  /** 用户token过期时间 */
  private Integer tokenTimeOut;

  /** 用户名手机号正则表达式 */
  private String mobilePattern;

  /** 短信验证码过期时间 */
  private Integer smsCodeTimeOut;

  /** 短信服务平台地址 */
  private String smsUrl;

  /** 短信平台机构代码 */
  private String smsOrgId;

  /** 短信平台用户名 */
  private String smsUserName;

  /** 短信平台密码 */
  private String smsPwd;



  /** 邮箱正则表达式 */
  private String emailPattern;
  /** 网站名称 */
  private String siteName;

  /** 发件人邮箱 */
  private String smtpFromMail;

  /** SMTP服务器地址 */
  private String smtpHost;

  /** SMTP服务器端口 */
  private Integer smtpPort;

  /** SMTP用户名 */
  private String smtpUsername;

  /** SMTP密码 */
  private String smtpPassword;

  /** 默认的模糊查询下拉菜单中返回的记录条数 */
  private Integer defaultPageSize;

  /** 极光平台注册应用后生成的appKey */
  private String appKey;

  /** 极光平台注册应用后生成的masterSecret */
  private String masterSecret;

  /**
   * 网站域名
   */
  private String siteUrl;

  /**
   * app用户查询商家时的搜索半径(单位：米)
   */
  private Integer searchRadius;

  /**
   * 保险类别ID
   */
  private Long insuranceId;

  /**
   * 公众平台appid
   */
  private String wxPublicAppId;

  /**
   * 公众平台appsecret
   */
  private String wxPublicAppSecret;

  /**
   * 秘钥
   */
  private String wechatKey;

  /**
   * 微信分配的公众账号ID（企业号corpid即为此appId）
   */
  private String wechatAppid;

  /**
   * 商户ID
   */
  private String wechatMchId;

  /**
   * 微信退款接口
   */
  private String wechatRefundUrl;

  /**
   * 微信证书地址
   */
  private String pkcs12Path;



  /**
   * 应用APPID
   */
  private String alipayAppId;


  /**
   * 支付宝商户的私钥
   */
  private String alipayPrivateKey;

  /**
   * 支付宝的公钥
   */
  private String alipayPublicKey;


  /**
   * jwt token key
   */
  private String tokenKey;


  public String getWechatRefundUrl() {
    return wechatRefundUrl;
  }

  public void setWechatRefundUrl(String wechatRefundUrl) {
    this.wechatRefundUrl = wechatRefundUrl;
  }

  public String getPkcs12Path() {
    return pkcs12Path;
  }

  public void setPkcs12Path(String pkcs12Path) {
    this.pkcs12Path = pkcs12Path;
  }

  public String getWxPublicAppId() {
    return wxPublicAppId;
  }

  public void setWxPublicAppId(String wxPublicAppId) {
    this.wxPublicAppId = wxPublicAppId;
  }

  public String getWxPublicAppSecret() {
    return wxPublicAppSecret;
  }

  public void setWxPublicAppSecret(String wxPublicAppSecret) {
    this.wxPublicAppSecret = wxPublicAppSecret;
  }

  public String getAlipayAppId() {
    return alipayAppId;
  }

  public void setAlipayAppId(String alipayAppId) {
    this.alipayAppId = alipayAppId;
  }

  public String getTokenKey() {
    return tokenKey;
  }

  public void setTokenKey(String tokenKey) {
    this.tokenKey = tokenKey;
  }


  public String getAlipayPrivateKey() {
    return alipayPrivateKey;
  }

  public void setAlipayPrivateKey(String alipayPrivateKey) {
    this.alipayPrivateKey = alipayPrivateKey;
  }

  public String getAlipayPublicKey() {
    return alipayPublicKey;
  }

  public void setAlipayPublicKey(String alipayPublicKey) {
    this.alipayPublicKey = alipayPublicKey;
  }


  public String getSmsUrl() {
    return smsUrl;
  }

  public void setSmsUrl(String smsUrl) {
    this.smsUrl = smsUrl;
  }

  public String getSmsOrgId() {
    return smsOrgId;
  }

  public void setSmsOrgId(String smsOrgId) {
    this.smsOrgId = smsOrgId;
  }

  public String getSmsUserName() {
    return smsUserName;
  }

  public void setSmsUserName(String smsUserName) {
    this.smsUserName = smsUserName;
  }

  public String getSmsPwd() {
    return smsPwd;
  }

  public void setSmsPwd(String smsPwd) {
    this.smsPwd = smsPwd;
  }



  public String getWechatKey() {
    return wechatKey;
  }

  public void setWechatKey(String wechatKey) {
    this.wechatKey = wechatKey;
  }

  public String getWechatAppid() {
    return wechatAppid;
  }

  public void setWechatAppid(String wechatAppid) {
    this.wechatAppid = wechatAppid;
  }

  public String getWechatMchId() {
    return wechatMchId;
  }

  public void setWechatMchId(String wechatMchId) {
    this.wechatMchId = wechatMchId;
  }


  public Long getInsuranceId() {
    return insuranceId;
  }

  public void setInsuranceId(Long insuranceId) {
    this.insuranceId = insuranceId;
  }



  public Integer getSearchRadius() {
    return searchRadius;
  }

  public void setSearchRadius(Integer searchRadius) {
    this.searchRadius = searchRadius;
  }

  public Integer getPasswordMaxlength() {
    return passwordMaxlength;
  }

  public void setPasswordMaxlength(Integer passwordMaxlength) {
    this.passwordMaxlength = passwordMaxlength;
  }

  public Integer getPasswordMinlength() {
    return passwordMinlength;
  }

  public void setPasswordMinlength(Integer passwordMinlength) {
    this.passwordMinlength = passwordMinlength;
  }

  public String getServerPublicKey() {
    return serverPublicKey;
  }

  public void setServerPublicKey(String serverPublicKey) {
    this.serverPublicKey = serverPublicKey;
  }

  public String getServerPrivateKey() {
    return serverPrivateKey;
  }

  public void setServerPrivateKey(String serverPrivateKey) {
    this.serverPrivateKey = serverPrivateKey;
  }

  public Integer getTokenTimeOut() {
    return tokenTimeOut;
  }

  public void setTokenTimeOut(Integer tokenTimeOut) {
    this.tokenTimeOut = tokenTimeOut;
  }

  public String getMobilePattern() {
    return mobilePattern;
  }

  public void setMobilePattern(String mobilePattern) {
    this.mobilePattern = mobilePattern;
  }


  public Integer getSmsCodeTimeOut() {
    return smsCodeTimeOut;
  }

  public void setSmsCodeTimeOut(Integer smsCodeTimeOut) {
    this.smsCodeTimeOut = smsCodeTimeOut;
  }



  /**
   * 获取发件人邮箱
   * 
   * @return 发件人邮箱
   */
  public String getSmtpFromMail() {
    return smtpFromMail;
  }

  /**
   * 设置发件人邮箱
   * 
   * @param smtpFromMail 发件人邮箱
   */
  public void setSmtpFromMail(String smtpFromMail) {
    this.smtpFromMail = smtpFromMail;
  }

  /**
   * 获取SMTP服务器地址
   * 
   * @return SMTP服务器地址
   */
  public String getSmtpHost() {
    return smtpHost;
  }

  /**
   * 设置SMTP服务器地址
   * 
   * @param smtpHost SMTP服务器地址
   */
  public void setSmtpHost(String smtpHost) {
    this.smtpHost = smtpHost;
  }

  /**
   * 获取SMTP服务器端口
   * 
   * @return SMTP服务器端口
   */
  public Integer getSmtpPort() {
    return smtpPort;
  }

  /**
   * 设置SMTP服务器端口
   * 
   * @param smtpPort SMTP服务器端口
   */
  public void setSmtpPort(Integer smtpPort) {
    this.smtpPort = smtpPort;
  }

  /**
   * 获取SMTP用户名
   * 
   * @return SMTP用户名
   */
  public String getSmtpUsername() {
    return smtpUsername;
  }

  /**
   * 设置SMTP用户名
   * 
   * @param smtpUsername SMTP用户名
   */
  public void setSmtpUsername(String smtpUsername) {
    this.smtpUsername = smtpUsername;
  }

  /**
   * 获取SMTP密码
   * 
   * @return SMTP密码
   */
  public String getSmtpPassword() {
    return smtpPassword;
  }

  /**
   * 设置SMTP密码
   * 
   * @param smtpPassword SMTP密码
   */
  public void setSmtpPassword(String smtpPassword) {
    this.smtpPassword = smtpPassword;
  }

  public String getEmailPattern() {
    return emailPattern;
  }

  public void setEmailPattern(String emailPattern) {
    this.emailPattern = emailPattern;
  }

  public String getSiteName() {
    return siteName;
  }

  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }

  public Integer getDefaultPageSize() {
    return defaultPageSize;
  }

  public void setDefaultPageSize(Integer defaultPageSize) {
    this.defaultPageSize = defaultPageSize;
  }

  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  public String getMasterSecret() {
    return masterSecret;
  }

  public void setMasterSecret(String masterSecret) {
    this.masterSecret = masterSecret;
  }

  public String getSiteUrl() {
    return siteUrl;
  }

  public void setSiteUrl(String siteUrl) {
    this.siteUrl = siteUrl;
  }

  public CaptchaType[] getCaptchaTypes() {
    return captchaTypes;
  }

  public void setCaptchaTypes(CaptchaType[] captchaTypes) {
    this.captchaTypes = captchaTypes;
  }
}
