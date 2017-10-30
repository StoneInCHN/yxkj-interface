package com.yxkj.json.bean;

import java.util.Date;
import java.util.List;

public class DailySumSupplementRecord {

  private Date date;
  
  private Integer SumWaitSupplyCount;
  
  private Integer SumSupplyCount;
  
  private List<SupplyRecord> supplementList;
  
  public class SupplyRecord {
    
    private String sceneSn;
    
    private String sceneName;
    
    private Integer waitSupplyCount;
    
    private Integer supplyCount;
    
    private Integer lackCount;
    
    private Date supplyTime;

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

    public Date getSupplyTime() {
      return supplyTime;
    }

    public void setSupplyTime(Date supplyTime) {
      this.supplyTime = supplyTime;
    }
    
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
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

  public List<SupplyRecord> getSupplementList() {
    return supplementList;
  }

  public void setSupplementList(List<SupplyRecord> supplementList) {
    this.supplementList = supplementList;
  }
  
}