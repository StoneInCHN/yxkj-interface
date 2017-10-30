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

  /** 登录验证码消息内容 */
  private String smsContentTemp;


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


  /**
   * 网站域名
   */
  private String siteUrl;


  /**
   * jwt token key
   */
  private String tokenKey;

  /**
   * 公众平台appid
   */
  private String wxPublicAppId;

  /**
   * 公众平台appsecret
   */
  private String wxPublicAppSecret;

  /**
   * 应用APPID
   */
  private String alipayAppId;

  /**
   * 支付宝合作身份者ID
   */
  private String alipayPartner;

  // /**
  // * 开发者私钥(合作伙伴密钥)
  // */
  // private String alipayPartnerPrivateKey;
  //
  // /**
  // * 支付宝的公钥(合作伙伴密钥)
  // */
  // private String alipayPartnerPublicKey;

  /**
   * 支付宝商户的私钥
   */
  private String alipayPrivateKey;

  /**
   * 支付宝的公钥
   */
  private String alipayPublicKey;

  /**
   * 支付宝异步通知接口
   */
  private String alipayNotifyUrl;

  /**
   * 支付宝同步通知接口
   */
  private String alipayReturnUrl;


  /**
   * 签约卖家支付宝账号
   */
  private String alipaySellerId;

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
   * 通知地址
   */
  private String wechatNotifyUrl;

  /**
   * 微信下订单接口
   */
  private String wechatAddOrderUrl;

  /**
   * 微信退款接口
   */
  private String wechatRefundUrl;

  /**
   * 微信证书地址
   */
  private String pkcs12Path;
  /**
   * 微信Token接口
   */
  private String wechatTokenUrl;


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


  public String getWechatNotifyUrl() {
    return wechatNotifyUrl;
  }


  public void setWechatNotifyUrl(String wechatNotifyUrl) {
    this.wechatNotifyUrl = wechatNotifyUrl;
  }


  public String getWechatAddOrderUrl() {
    return wechatAddOrderUrl;
  }


  public void setWechatAddOrderUrl(String wechatAddOrderUrl) {
    this.wechatAddOrderUrl = wechatAddOrderUrl;
  }


  public String getWechatTokenUrl() {
    return wechatTokenUrl;
  }


  public void setWechatTokenUrl(String wechatTokenUrl) {
    this.wechatTokenUrl = wechatTokenUrl;
  }


  public String getAlipayAppId() {
    return alipayAppId;
  }


  public void setAlipayAppId(String alipayAppId) {
    this.alipayAppId = alipayAppId;
  }


  public String getAlipayPartner() {
    return alipayPartner;
  }


  public void setAlipayPartner(String alipayPartner) {
    this.alipayPartner = alipayPartner;
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


  public String getAlipayNotifyUrl() {
    return alipayNotifyUrl;
  }


  public void setAlipayNotifyUrl(String alipayNotifyUrl) {
    this.alipayNotifyUrl = alipayNotifyUrl;
  }


  public String getAlipayReturnUrl() {
    return alipayReturnUrl;
  }


  public void setAlipayReturnUrl(String alipayReturnUrl) {
    this.alipayReturnUrl = alipayReturnUrl;
  }


  public String getAlipaySellerId() {
    return alipaySellerId;
  }


  public void setAlipaySellerId(String alipaySellerId) {
    this.alipaySellerId = alipaySellerId;
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


  public String getSmsContentTemp() {
    return smsContentTemp;
  }


  public void setSmsContentTemp(String smsContentTemp) {
    this.smsContentTemp = smsContentTemp;
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


  public String getSmtpFromMail() {
    return smtpFromMail;
  }


  public void setSmtpFromMail(String smtpFromMail) {
    this.smtpFromMail = smtpFromMail;
  }


  public String getSmtpHost() {
    return smtpHost;
  }


  public void setSmtpHost(String smtpHost) {
    this.smtpHost = smtpHost;
  }


  public Integer getSmtpPort() {
    return smtpPort;
  }


  public void setSmtpPort(Integer smtpPort) {
    this.smtpPort = smtpPort;
  }


  public String getSmtpUsername() {
    return smtpUsername;
  }


  public void setSmtpUsername(String smtpUsername) {
    this.smtpUsername = smtpUsername;
  }


  public String getSmtpPassword() {
    return smtpPassword;
  }


  public void setSmtpPassword(String smtpPassword) {
    this.smtpPassword = smtpPassword;
  }


  public Integer getDefaultPageSize() {
    return defaultPageSize;
  }


  public void setDefaultPageSize(Integer defaultPageSize) {
    this.defaultPageSize = defaultPageSize;
  }


  public String getSiteUrl() {
    return siteUrl;
  }


  public void setSiteUrl(String siteUrl) {
    this.siteUrl = siteUrl;
  }


  public String getTokenKey() {
    return tokenKey;
  }


  public void setTokenKey(String tokenKey) {
    this.tokenKey = tokenKey;
  }


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
}
