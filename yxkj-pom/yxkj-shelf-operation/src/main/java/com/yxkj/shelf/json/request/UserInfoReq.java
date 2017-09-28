package com.yxkj.shelf.json.request;

import java.util.ArrayList;
import java.util.List;


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
   * 商品条码
   */
  private String gId;

  /**
   * 公司ID
   */
  private Long compId;

  /**
   * 支付类型 微信: wx 支付宝: alipay
   */
  private String type;

  /**
   * 用户名
   */
  private String userName;

  /**
   * 下单商品信息
   */
  private List<String> gInfo = new ArrayList<String>();

  /**
   * 客户端ip
   */
  private String ip = "127.0.0.1";


  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public List<String> getgInfo() {
    return gInfo;
  }

  public void setgInfo(List<String> gInfo) {
    this.gInfo = gInfo;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

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

  public String getgId() {
    return gId;
  }

  public void setgId(String gId) {
    this.gId = gId;
  }

  public Long getCompId() {
    return compId;
  }

  public void setCompId(Long compId) {
    this.compId = compId;
  }

}
