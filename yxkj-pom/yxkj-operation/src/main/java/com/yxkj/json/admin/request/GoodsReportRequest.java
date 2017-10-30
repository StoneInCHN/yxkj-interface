package com.yxkj.json.admin.request;

import java.util.Date;

import com.yxkj.json.base.BaseListRequest;

public class GoodsReportRequest extends BaseListRequest {

  /**
   * 优享空间ID
   */
  private Long sceneId;
  /**
   * 优享空间
   */
  private Long gCateId;

  /**
   * 商品条码
   */
  private String gCode;

  /**
   * 商品名称
   */
  private String gName;

  /**
   * 开始日期
   */
  private Date startTime;

  /**
   * 结束日期
   */
  private Date endTime;



  public Long getSceneId() {
    return sceneId;
  }

  public void setSceneId(Long sceneId) {
    this.sceneId = sceneId;
  }

  public Long getgCateId() {
    return gCateId;
  }

  public void setgCateId(Long gCateId) {
    this.gCateId = gCateId;
  }

  public String getgCode() {
    return gCode;
  }

  public void setgCode(String gCode) {
    this.gCode = gCode;
  }

  public String getgName() {
    return gName;
  }

  public void setgName(String gName) {
    this.gName = gName;
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
