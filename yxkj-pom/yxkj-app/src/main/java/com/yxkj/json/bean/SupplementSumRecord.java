package com.yxkj.json.bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SupplementSumRecord {
  
  private LocalDate supplyDate;
  
  private Integer sumWaitSuppCount;
  
  private Integer sumSuppCount;
  
  List<SceneSupplyRecord> sceneSupplyRecords;
  
  public LocalDate getSupplyDate() {
    return supplyDate;
  }

  public void setSupplyDate(LocalDate supplyDate) {
    this.supplyDate = supplyDate;
  }

  public Integer getSumWaitSuppCount() {
    return sumWaitSuppCount;
  }

  public void setSumWaitSuppCount(Integer sumWaitSuppCount) {
    this.sumWaitSuppCount = sumWaitSuppCount;
  }

  public Integer getSumSuppCount() {
    return sumSuppCount;
  }

  public void setSumSuppCount(Integer sumSuppCount) {
    this.sumSuppCount = sumSuppCount;
  }

  public List<SceneSupplyRecord> getSceneSupplyRecords() {
    return sceneSupplyRecords;
  }

  public void setSceneSupplyRecords(List<SceneSupplyRecord> sceneSupplyRecords) {
    this.sceneSupplyRecords = sceneSupplyRecords;
  }

  public class SceneSupplyRecord {
    
    private String sceneSn;
    
    private String sceneName;
    
    private Integer suppTotalCount;
    
    private Integer waitSuppTotalCount;
    
    private Integer lackCount;
    
    private LocalDateTime suppEndTime;

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

    public Integer getSuppTotalCount() {
      return suppTotalCount;
    }

    public void setSuppTotalCount(Integer suppTotalCount) {
      this.suppTotalCount = suppTotalCount;
    }

    public Integer getWaitSuppTotalCount() {
      return waitSuppTotalCount;
    }

    public void setWaitSuppTotalCount(Integer waitSuppTotalCount) {
      this.waitSuppTotalCount = waitSuppTotalCount;
    }

    public Integer getLackCount() {
      return lackCount;
    }

    public void setLackCount(Integer lackCount) {
      this.lackCount = lackCount;
    }

    public LocalDateTime getSuppEndTime() {
      return suppEndTime;
    }

    public void setSuppEndTime(LocalDateTime suppEndTime) {
      this.suppEndTime = suppEndTime;
    }
    
  }
  
}
