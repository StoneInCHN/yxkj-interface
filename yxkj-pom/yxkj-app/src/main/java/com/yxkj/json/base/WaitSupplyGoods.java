package com.yxkj.json.base;

public class WaitSupplyGoods {
    
  /**
   * 商品编号
   */
  private String goodsSn;
  
  /**
   * 商品名称
   */
  private String goodsName;
  
  /**
   * 商品图片
   */
  private String goodsPic;
  
  /**
   * 待补货数
   */
  private Integer waitSupplyCount;
  
  public WaitSupplyGoods(){}

  public WaitSupplyGoods(String goodsSn, String goodsName, Integer waitSupplyCount) {
    this.goodsSn = goodsSn;
    this.goodsName = goodsName;
    this.waitSupplyCount = waitSupplyCount;
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
  
}

