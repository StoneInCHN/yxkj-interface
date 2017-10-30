package com.yxkj.json.request;

import java.util.ArrayList;
import java.util.List;

import com.yxkj.json.base.BaseRequest;

/**
 *
 * 用户req
 * 
 * @author Andrea
 * @version 创建时间：2017年9月26日 下午6:21:14
 * 
 */
public class OrderInfoReq extends BaseRequest {


  /**
   * 下单商品信息
   */
  private List<String> gInfo = new ArrayList<String>();

  /**
   * 客户端ip
   */
  private String ip = "127.0.0.1";

  /**
   * 扫码类型
   */
  private String type;

  /**
   * 中控imei号
   */
  private String imei;

  /**
   * 订单号
   */
  private String orderSn;

  public List<String> getgInfo() {
    return gInfo;
  }

  public void setgInfo(List<String> gInfo) {
    this.gInfo = gInfo;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getImei() {
    return imei;
  }

  public void setImei(String imei) {
    this.imei = imei;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getOrderSn() {
    return orderSn;
  }

  public void setOrderSn(String orderSn) {
    this.orderSn = orderSn;
  }
}
