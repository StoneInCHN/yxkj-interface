package com.yxkj.json.admin.request;

import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.json.base.BaseListRequest;
import com.yxkj.json.base.BaseRequest;

public class AdResourceListRequest extends BaseListRequest {

  private CommonEnum.FileType fileType;

  public CommonEnum.FileType getFileType() {
    return fileType;
  }

  public void setFileType(CommonEnum.FileType fileType) {
    this.fileType = fileType;
  }
}
