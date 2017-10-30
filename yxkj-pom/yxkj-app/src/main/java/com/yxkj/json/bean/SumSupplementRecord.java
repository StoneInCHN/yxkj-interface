package com.yxkj.json.bean;

public class SumSupplementRecord {
  /**
   * 优享空间编号
   */
  private String sceneSn;

  /**
   * 优享空间地址名称
   */
  private String sceneName;

  /**
   * 总待补货数量
   */
  private Integer waitSuppTotalCount;

  /**
   * 实际总补货数量
   */
  private Integer suppTotalCount;

  /**
   * 缺货数量
   */
  private Integer lackCount;

  /**
   * 补货完成时间
   */
  private String suppEndTime;

  public SumSupplementRecord() {}
  
  public SumSupplementRecord(String sceneSn, String sceneName, Integer waitSuppTotalCount,
      Integer suppTotalCount, Integer lackCount, String suppEndTime) {
    this.sceneSn = sceneSn;
    this.sceneName = sceneName;
    this.waitSuppTotalCount = waitSuppTotalCount;
    this.suppTotalCount = suppTotalCount;
    this.lackCount = lackCount;
    this.suppEndTime = suppEndTime;
  }

  public String getSceneSn() {
    return sceneSn;
  }

  public void setSceneSn(String sceneSn) {
    this.sceneSn = sceneSn;
  }

  public String getSceneName() {
    return sceneName;
  }

  public void setSceneName(String sceneName) {
    this.sceneName = sceneName;
  }

  public Integer getWaitSuppTotalCount() {
    return waitSuppTotalCount;
  }

  public void setWaitSuppTotalCount(Integer waitSuppTotalCount) {
    this.waitSuppTotalCount = waitSuppTotalCount;
  }

  public Integer getSuppTotalCount() {
    return suppTotalCount;
  }

  public void setSuppTotalCount(Integer suppTotalCount) {
    this.suppTotalCount = suppTotalCount;
  }

  public Integer getLackCount() {
    return lackCount;
  }

  public void setLackCount(Integer lackCount) {
    this.lackCount = lackCount;
  }

  public String getSuppEndTime() {
    return suppEndTime;
  }

  public void setSuppEndTime(String suppEndTime) {
    this.suppEndTime = suppEndTime;
  }
  
}
