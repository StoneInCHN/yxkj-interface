package com.yxkj.shelf.beans;

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
   * 通知地址
   */
  private String wechatNotifyUrl;

  /**
   * 微信下订单接口
   */
  private String wechatAddOrderUrl;

  /**
   * 微信Token接口
   */
  private String wechatTokenUrl;



  /**
   * 百度API坐标转换
   */
  private String convertMapUrl;

  /**
   * 百度API地址转坐标
   */
  private String convertAddressUrl;

  /**
   * 百度地图api key
   */
  private String mapAk;

  /**
   * 百度车联网api url
   */
  private String bdCarMapUrl;

  /**
   * 百度车联网api key
   */
  private String bdCarMapAk;

  /**
   * 百度车联网api mcode
   */
  private String bdCarMcode;

  /**
   * 百度车联网api 分页 每页记录数
   */
  private String bdNumber;

  /**
   * 百度车联网api 分页 页数
   */
  private String bdPage;

  /**
   * 百度API Store api key
   */
  private String bdApiStoreKey;

  /**
   * 百度API Store api 汉字转拼音
   */
  private String bdApiStoreHz2PyUrl;

  /**
   * 百度API Store api 获取生活指数
   */
  private String bdApiStoreWeatherUrl;

  /**
   * 百度车联网API 坐标修正
   */
  private String bdCarConvert;


  /**
   * 对外接口APIKEY
   */
  private String intfApiKey;

  /**
   * 新闻详情url
   */
  private String newsDetailsUrl;

  /**
   * 商品订单过期间隔时间(分钟)
   */
  private Integer orderTimeOut;

  /**
   * 商品订单默认自动收货天数(从发货日期计算)
   */
  private Integer orderReceiveTimeOut;

  /**
   * 商品订单默认自动收货天数(从发货日期计算)
   */
  private Integer orderCompleteTimeOut;

  /**
   * 授权跳转url
   */
  private String authRedirectUrl;

  /**
   * 下载页面
   */
  private String downloadPage;

  /**
   * 下载链接
   */
  private String downloadUrl;

  /**
   * 短信后缀
   */
  private String smsPostfix;

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
   * 聚合数据 银行卡四元素验证URL
   */
  private String juheVerifyBankcard4;
  /**
   * 聚合数据 申请的key
   */
  private String juheKey;

  /**
   * 聚合数据 证件识别URL
   */
  private String juheVerifyCertificates;
  /**
   * 聚合数据 证件识别key
   */
  private String juheKeyCertificates;

  /**
   * jwt token key
   */
  private String tokenKey;

  public String getAlipayReturnUrl() {
    return alipayReturnUrl;
  }

  public void setAlipayReturnUrl(String alipayReturnUrl) {
    this.alipayReturnUrl = alipayReturnUrl;
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

  public String getJuheVerifyCertificates() {
    return juheVerifyCertificates;
  }

  public void setJuheVerifyCertificates(String juheVerifyCertificates) {
    this.juheVerifyCertificates = juheVerifyCertificates;
  }

  public String getJuheKeyCertificates() {
    return juheKeyCertificates;
  }

  public void setJuheKeyCertificates(String juheKeyCertificates) {
    this.juheKeyCertificates = juheKeyCertificates;
  }

  public String getDownloadPage() {
    return downloadPage;
  }

  public void setDownloadPage(String downloadPage) {
    this.downloadPage = downloadPage;
  }

  public String getDownloadUrl() {
    return downloadUrl;
  }

  public void setDownloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
  }


  public String getAlipaySellerId() {
    return alipaySellerId;
  }

  public void setAlipaySellerId(String alipaySellerId) {
    this.alipaySellerId = alipaySellerId;
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

  public String getSmsPostfix() {
    return smsPostfix;
  }

  public void setSmsPostfix(String smsPostfix) {
    this.smsPostfix = smsPostfix;
  }

  public String getSmsContentTemp() {
    return smsContentTemp;
  }

  public void setSmsContentTemp(String smsContentTemp) {
    this.smsContentTemp = smsContentTemp;
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


  public Integer getOrderReceiveTimeOut() {
    return orderReceiveTimeOut;
  }

  public void setOrderReceiveTimeOut(Integer orderReceiveTimeOut) {
    this.orderReceiveTimeOut = orderReceiveTimeOut;
  }

  public Integer getOrderCompleteTimeOut() {
    return orderCompleteTimeOut;
  }

  public void setOrderCompleteTimeOut(Integer orderCompleteTimeOut) {
    this.orderCompleteTimeOut = orderCompleteTimeOut;
  }

  public Integer getOrderTimeOut() {
    return orderTimeOut;
  }

  public void setOrderTimeOut(Integer orderTimeOut) {
    this.orderTimeOut = orderTimeOut;
  }

  public String getNewsDetailsUrl() {
    return newsDetailsUrl;
  }

  public void setNewsDetailsUrl(String newsDetailsUrl) {
    this.newsDetailsUrl = newsDetailsUrl;
  }

  public String getIntfApiKey() {
    return intfApiKey;
  }

  public void setIntfApiKey(String intfApiKey) {
    this.intfApiKey = intfApiKey;
  }

  public String getBdApiStoreKey() {
    return bdApiStoreKey;
  }

  public void setBdApiStoreKey(String bdApiStoreKey) {
    this.bdApiStoreKey = bdApiStoreKey;
  }

  public String getBdApiStoreHz2PyUrl() {
    return bdApiStoreHz2PyUrl;
  }

  public void setBdApiStoreHz2PyUrl(String bdApiStoreHz2PyUrl) {
    this.bdApiStoreHz2PyUrl = bdApiStoreHz2PyUrl;
  }

  public String getBdApiStoreWeatherUrl() {
    return bdApiStoreWeatherUrl;
  }

  public void setBdApiStoreWeatherUrl(String bdApiStoreWeatherUrl) {
    this.bdApiStoreWeatherUrl = bdApiStoreWeatherUrl;
  }

  public String getBdCarConvert() {
    return bdCarConvert;
  }

  public void setBdCarConvert(String bdCarConvert) {
    this.bdCarConvert = bdCarConvert;
  }

  public String getBdCarMapUrl() {
    return bdCarMapUrl;
  }

  public void setBdCarMapUrl(String bdCarMapUrl) {
    this.bdCarMapUrl = bdCarMapUrl;
  }

  public String getBdCarMapAk() {
    return bdCarMapAk;
  }

  public void setBdCarMapAk(String bdCarMapAk) {
    this.bdCarMapAk = bdCarMapAk;
  }

  public String getBdCarMcode() {
    return bdCarMcode;
  }

  public void setBdCarMcode(String bdCarMcode) {
    this.bdCarMcode = bdCarMcode;
  }

  public String getBdNumber() {
    return bdNumber;
  }

  public void setBdNumber(String bdNumber) {
    this.bdNumber = bdNumber;
  }

  public String getBdPage() {
    return bdPage;
  }

  public void setBdPage(String bdPage) {
    this.bdPage = bdPage;
  }

  public String getMapAk() {
    return mapAk;
  }

  public void setMapAk(String mapAk) {
    this.mapAk = mapAk;
  }

  public String getConvertAddressUrl() {
    return convertAddressUrl;
  }

  public void setConvertAddressUrl(String convertAddressUrl) {
    this.convertAddressUrl = convertAddressUrl;
  }


  public String getConvertMapUrl() {
    return convertMapUrl;
  }

  public void setConvertMapUrl(String convertMapUrl) {
    this.convertMapUrl = convertMapUrl;
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

  public String getJuheVerifyBankcard4() {
    return juheVerifyBankcard4;
  }

  public void setJuheVerifyBankcard4(String juheVerifyBankcard4) {
    this.juheVerifyBankcard4 = juheVerifyBankcard4;
  }

  public String getJuheKey() {
    return juheKey;
  }

  public void setJuheKey(String juheKey) {
    this.juheKey = juheKey;
  }

  public String getAuthRedirectUrl() {
    return authRedirectUrl;
  }

  public void setAuthRedirectUrl(String authRedirectUrl) {
    this.authRedirectUrl = authRedirectUrl;
  }
  public CaptchaType[] getCaptchaTypes() {
	 return captchaTypes;
  }

  public void setCaptchaTypes(CaptchaType[] captchaTypes) {
	 this.captchaTypes = captchaTypes;
  }
}
