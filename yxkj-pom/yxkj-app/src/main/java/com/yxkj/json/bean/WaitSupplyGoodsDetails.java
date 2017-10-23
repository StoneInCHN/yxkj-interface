package com.yxkj.json.bean;

import java.util.List;
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
  private List<Map<String, Object>> sceneCountList;
  
  /**
   * 待补总数
   */
  private Integer sumCount;

  public WaitSupplyGoodsDetails(String goodsSn, String goodsName,
      List<Map<String, Object>> sceneCountList, Integer sumCount) {
    super();
    this.goodsSn = goodsSn;
    this.goodsName = goodsName;
    this.sceneCountList = sceneCountList;
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

  public List<Map<String, Object>> getSceneCountList() {
    return sceneCountList;
  }

  public void setSceneCountList(List<Map<String, Object>> sceneCountList) {
    this.sceneCountList = sceneCountList;
  }

  public Integer getSumCount() {
    return sumCount;
  }

  public void setSumCount(Integer sumCount) {
    this.sumCount = sumCount;
  }
  
  
}
