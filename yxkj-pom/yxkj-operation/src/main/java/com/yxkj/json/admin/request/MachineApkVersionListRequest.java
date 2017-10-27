package com.yxkj.json.admin.request;

import com.yxkj.json.base.BaseListRequest;

public class MachineApkVersionListRequest extends BaseListRequest {

  private String sceneName;

  public String getSceneName() {
    return sceneName;
  }

  public void setSceneName(String sceneName) {
    this.sceneName = sceneName;
  }
}
