package com.yxkj.shelf.json.request;

/**
 *
 * 用户req
 * 
 * @author Andrea
 * @version 创建时间：2017年9月26日 下午6:35:36
 * 
 */
public class UserInfoReq {



  /**
   * 支付宝或微信authCode
   */
  private String authCode;

  /**
   * 商品ID
   */
  private Long gId;

  /**
   * 公司ID
   */
  private Long compId;

  /**
   * 支付类型 微信: wx 支付宝: alipay
   */
  private String type;


  public String getAuthCode() {
    return authCode;
  }

  public void setAuthCode(String authCode) {
    this.authCode = authCode;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getgId() {
    return gId;
  }

  public void setgId(Long gId) {
    this.gId = gId;
  }

  public Long getCompId() {
    return compId;
  }

  public void setCompId(Long compId) {
    this.compId = compId;
  }

}
