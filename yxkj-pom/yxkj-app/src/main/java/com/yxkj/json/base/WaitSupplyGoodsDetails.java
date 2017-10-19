package com.yxkj.json.base;

import java.util.Map;

public class WaitSupplyGoodsDetails {

  /**
   * 商品编号
   */
  private String goodsSn;
  
  /**
   * 商品名称
   */
  private String goodsName;
  
  /**
   * 优享空间对应待补数量
   */
  private Map<String, Integer> sceneCountMap;
  
  /**
   * 待补总数
   */
  private Integer sumCount;
  
  public WaitSupplyGoodsDetails() {}
  
  public WaitSupplyGoodsDetails(String goodsSn, String goodsName, Map<String, Integer> sceneCountMap,
      Integer sumCount) {
    this.goodsSn = goodsSn;
    this.goodsName = goodsName;
    this.sceneCountMap = sceneCountMap;
    this.sumCount = sumCount;
  }

  public String getGoodsSn() {
    return goodsSn;
  }

  public void setGoodsSn(String goodsSn) {
    this.goodsSn = goodsSn;
  }

  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  public Map<String, Integer> getSceneCountMap() {
    return sceneCountMap;
  }

  public void setSceneCountMap(Map<String, Integer> sceneCountMap) {
    this.sceneCountMap = sceneCountMap;
  }

  public Integer getSumCount() {
    return sumCount;
  }

  public void setSumCount(Integer sumCount) {
    this.sumCount = sumCount;
  }
  
}
