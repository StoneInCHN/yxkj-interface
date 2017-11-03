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

  /**
   * 设备与上位机连接状态
   */
  private boolean connectStatus;

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

  public boolean getConnectStatus() {
    return connectStatus;
  }

  public void setConnectStatus(boolean connectStatus) {
    this.connectStatus = connectStatus;
  }
}
