package com.yxkj.json.bean;

import java.util.List;

public class DailySumSupplementRecord {

  /**
   * 补货日期
   */
  private String date;
  
  /**
   * 总待补数
   */
  private Integer SumWaitSupplyCount = 0;
  
  /**
   * 总补货数
   */
  private Integer SumSupplyCount = 0;
  
  /**
   * 补货记录
   */
  private List<SceneSumSupplementRecord> supplementList;
  
  public class SceneSumSupplementRecord {
    
    /**
     * 优享空间编号
     */
    private String sceneSn;
    
    /**
     * 优享空间名称
     */
    private String sceneName;
    
    /**
     * 待补数
     */
    private Integer waitSupplyCount;
    
    /**
     * 补货数
     */
    private Integer supplyCount;
    
    /**
     * 补货时间
     */
    private String supplyTime;
    
    public SceneSumSupplementRecord(String sceneSn, String sceneName, Integer waitSupplyCount,
        Integer supplyCount, String supplyTime) {
      super();
      this.sceneSn = sceneSn;
      this.sceneName = sceneName;
      this.waitSupplyCount = waitSupplyCount;
      this.supplyCount = supplyCount;
      this.supplyTime = supplyTime;
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

    public Integer getWaitSupplyCount() {
      return waitSupplyCount;
    }

    public void setWaitSupplyCount(Integer waitSupplyCount) {
      this.waitSupplyCount = waitSupplyCount;
    }

    public Integer getSupplyCount() {
      return supplyCount;
    }

    public void setSupplyCount(Integer supplyCount) {
      this.supplyCount = supplyCount;
    }

    public String getSupplyTime() {
      return supplyTime;
    }

    public void setSupplyTime(String supplyTime) {
      this.supplyTime = supplyTime;
    }
    
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Integer getSumWaitSupplyCount() {
    return SumWaitSupplyCount;
  }

  public void setSumWaitSupplyCount(Integer sumWaitSupplyCount) {
    SumWaitSupplyCount = sumWaitSupplyCount;
  }

  public Integer getSumSupplyCount() {
    return SumSupplyCount;
  }

  public void setSumSupplyCount(Integer sumSupplyCount) {
    SumSupplyCount = sumSupplyCount;
  }

  public List<SceneSumSupplementRecord> getSupplementList() {
    return supplementList;
  }

  public void setSupplementList(List<SceneSumSupplementRecord> supplementList) {
    this.supplementList = supplementList;
  }
  
}