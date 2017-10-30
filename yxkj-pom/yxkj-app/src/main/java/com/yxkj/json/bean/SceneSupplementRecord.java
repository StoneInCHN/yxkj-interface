package com.yxkj.json.bean;

import java.util.List;

public class SceneSupplementRecord {
  
  private String cntrSn;
  
  private List<CntrSupplementRecord> cntrSupplementRecords;
  
  public class CntrSupplementRecord {
    
    private String channelSn;
    
    private String goodsName;
    
    private String goodsPic;
    
    private Integer waitSupplyCount;
    
    private Integer supplyCount;

    public String getChannelSn() {
      return channelSn;
    }

    public void setChannelSn(String channelSn) {
      this.channelSn = channelSn;
    }

    public String getGoodsName() {
      return goodsName;
    }

    public void setGoodsName(String goodsName) {
      this.goodsName = goodsName;
    }

    public String getGoodsPic() {
      return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
      this.goodsPic = goodsPic;
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
    
  }

  public String getCntrSn() {
    return cntrSn;
  }

  public void setCntrSn(String cntrSn) {
    this.cntrSn = cntrSn;
  }

  public List<CntrSupplementRecord> getCntrSupplementRecords() {
    return cntrSupplementRecords;
  }

  public void setCntrSupplementRecords(List<CntrSupplementRecord> cntrSupplementRecords) {
    this.cntrSupplementRecords = cntrSupplementRecords;
  }
  
}
