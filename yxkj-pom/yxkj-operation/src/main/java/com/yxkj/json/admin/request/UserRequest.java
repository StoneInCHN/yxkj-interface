package com.yxkj.json.admin.request;

import java.util.Date;

import com.yxkj.json.base.BaseListRequest;

public class UserRequest extends BaseListRequest {

  /**
   * 优享空间ID
   */
  private Long sceneId;

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
