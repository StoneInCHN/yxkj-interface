package com.yxkj.json.admin.request;

import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.json.base.BaseRequest;

public class AdResourceRequest extends BaseRequest {

  /**
   * 文件名称
   */
  private String fileName;

  /**
   * 文件URL
   */
  private String fileUrl;

  /**
   * 备注
   */
  private String remark;


  /**
   * 广告类型
   */

  private CommonEnum.FileType fileType;

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public CommonEnum.FileType getFileType() {
    return fileType;
  }

  public void setFileType(CommonEnum.FileType fileType) {
    this.fileType = fileType;
  }

}
