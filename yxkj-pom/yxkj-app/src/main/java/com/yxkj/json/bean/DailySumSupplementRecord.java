package com.yxkj.json.bean;

import java.util.LinkedList;
import java.util.List;

public class DailySumSupplementRecord {

  private String date;
  
  private Integer SumWaitSupplyCount = 0;
  
  private Integer SumSupplyCount = 0;
  
  private List<SceneSumSupplementRecord> supplementList;
  
  public class SceneSumSupplementRecord {
    
    private String sceneSn;
    
    private String sceneName;
    
    private Integer waitSupplyCount;
    
    private Integer supplyCount;
    
    private Integer lackCount;
    
    private String supplyTime;
    
    public SceneSumSupplementRecord(String sceneSn, String sceneName, Integer waitSupplyCount,
        Integer supplyCount, Integer lackCount, String supplyTime) {
      super();
      this.sceneSn = sceneSn;
      this.sceneName = sceneName;
      this.waitSupplyCount = waitSupplyCount;
      this.supplyCount = supplyCount;
      this.lackCount = lackCount;
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

    public Integer getLackCount() {
      return lackCount;
    }

    public void setLackCount(Integer lackCount) {
      this.lackCount = lackCount;
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