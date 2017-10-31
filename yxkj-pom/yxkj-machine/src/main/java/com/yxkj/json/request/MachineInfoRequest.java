package com.yxkj.json.request;

public class MachineInfoRequest {

  /**
   * 设备号
   */
  private String deviceNo;
  /**
   * 音量
   */
  private String volume;

  public String getVolume() {
    return volume;
  }

  public void setVolume(String volume) {
    this.volume = volume;
  }

  public String getDeviceNo() {
    return deviceNo;
  }

  public void setDeviceNo(String deviceNo) {
    this.deviceNo = deviceNo;
  }
}
