package com.yxkj.json.admin.request;

import com.yxkj.json.base.BaseRequest;

public class AdMachineRequest extends BaseRequest {

  /**
   * 视频A
   */
  private String advA;

  /**
   * 视频B
   */
  private String advB;

  /**
   * 图片1
   */
  private String advC;

  /**
   * 图片2
   */
  private String advD;

  /**
   * 图片3
   */
  private String advE;


  /**
   * 备注
   */
  private String remark;

  public String getAdvA() {
    return advA;
  }

  public void setAdvA(String advA) {
    this.advA = advA;
  }

  public String getAdvB() {
    return advB;
  }

  public void setAdvB(String advB) {
    this.advB = advB;
  }

  public String getAdvC() {
    return advC;
  }

  public void setAdvC(String advC) {
    this.advC = advC;
  }

  public String getAdvD() {
    return advD;
  }

  public void setAdvD(String advD) {
    this.advD = advD;
  }

  public String getAdvE() {
    return advE;
  }

  public void setAdvE(String advE) {
    this.advE = advE;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
