package com.yxkj.json.admin.request;

import com.yxkj.json.base.BaseRequest;

import java.util.List;

public class MachineApkVersionRequest extends BaseRequest {

  /**
   * 版本号
   */
  private String versionName;

  /**
   * 版本序列号(用于比较是否是新版本)
   */
  private Integer versionCode = 0;
  /**
   * 文件访问地址
   */
  private String apkPath;

  /**
   * 版本更新内容
   */
  private String updateContent;

  /**
   * 更新的优享空间
   */
  private List<Long> sceneIds;

  public String getVersionName() {
    return versionName;
  }

  public void setVersionName(String versionName) {
    this.versionName = versionName;
  }

  public Integer getVersionCode() {
    return versionCode;
  }

  public void setVersionCode(Integer versionCode) {
    this.versionCode = versionCode;
  }

  public String getApkPath() {
    return apkPath;
  }

  public void setApkPath(String apkPath) {
    this.apkPath = apkPath;
  }

  public String getUpdateContent() {
    return updateContent;
  }

  public void setUpdateContent(String updateContent) {
    this.updateContent = updateContent;
  }

  public List<Long> getSceneIds() {
    return sceneIds;
  }

  public void setSceneIds(List<Long> sceneIds) {
    this.sceneIds = sceneIds;
  }
}
