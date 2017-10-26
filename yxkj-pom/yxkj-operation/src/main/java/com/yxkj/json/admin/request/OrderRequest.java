package com.yxkj.json.admin.request;

import java.util.Date;

import com.yxkj.entity.commonenum.CommonEnum.OrderStatus;
import com.yxkj.entity.commonenum.CommonEnum.PurMethod;
import com.yxkj.json.base.BaseListRequest;

public class OrderRequest extends BaseListRequest {

  /**
   * 优享空间ID
   */
  private Long sceneId;
  /**
   * 优享空间
   */
  private String sceneName;

  /**
   * 订单号
   */
  private String orderSn;

  /**
   * 用户名
   */
  private String userName;

  /**
   * 开始日期
   */
  private Date startTime;

  /**
   * 结束日期
   */
  private Date endTime;

  /**
   * 购买方式
   */
  private PurMethod purMethod;

  /**
   * 支付方式ID
   */
  private Long paymentTypeId;

  /**
   * 订单状态
   */
  private OrderStatus status;

  /**
   * 重复购买次数
   */
  private Integer repeatCount;


  public Long getSceneId() {
    return sceneId;
  }

  public void setSceneId(Long sceneId) {
    this.sceneId = sceneId;
  }

  public Integer getRepeatCount() {
    return repeatCount;
  }

  public void setRepeatCount(Integer repeatCount) {
    this.repeatCount = repeatCount;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public String getSceneName() {
    return sceneName;
  }

  public void setSceneName(String sceneName) {
    this.sceneName = sceneName;
  }

  public String getOrderSn() {
    return orderSn;
  }

  public void setOrderSn(String orderSn) {
    this.orderSn = orderSn;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public PurMethod getPurMethod() {
    return purMethod;
  }

  public void setPurMethod(PurMethod purMethod) {
    this.purMethod = purMethod;
  }

  public Long getPaymentTypeId() {
    return paymentTypeId;
  }

  public void setPaymentTypeId(Long paymentTypeId) {
    this.paymentTypeId = paymentTypeId;
  }


  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }


}
