package com.yxkj.json.admin.request;

import com.yxkj.json.base.BaseListRequest;

public class AdMachineListRequest extends BaseListRequest {

  /**
   * 优享空间名称
   */
  private String sceneName;

  /**
   * 优享空间编号
   */
  private String sceneSn;


  public String getSceneName() {
    return sceneName;
  }

  public void setSceneName(String sceneName) {
    this.sceneName = sceneName;
  }

  public String getSceneSn() {
    return sceneSn;
  }

  public void setSceneSn(String sceneSn) {
    this.sceneSn = sceneSn;
  }
}
