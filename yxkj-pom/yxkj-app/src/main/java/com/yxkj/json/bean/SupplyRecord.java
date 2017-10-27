package com.yxkj.json.bean;

public class SupplyRecord {
  
  private  Long supplementId;
  
  private Integer supplyCount;

  public Long getSupplementId() {
    return supplementId;
  }

  public void setSupplementId(Long supplementId) {
    this.supplementId = supplementId;
  }

  public Integer getSupplyCount() {
    return supplyCount;
  }

  public void setSupplyCount(Integer supplyCount) {
    this.supplyCount = supplyCount;
  }

  @Override
  public String toString() {
    return "SupplyRecord [supplementId=" + supplementId + ", supplyCount=" + supplyCount + "]";
  }
}