package com.yxkj.json.bean;

public class WaitSupplyContainerGoods {
  
  /**
   * 待补清单id
   */
  private Long id;
  
  /**
   * 货道编号
   */
  private String channelSn;
  
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
  
  /**
   * 剩余数
   */
  private Integer remainCount;

  public WaitSupplyContainerGoods(Long id, String goodsSn, String goodsName, String channelSn, 
      Integer waitSupplyCount, Integer remainCount) {
    this.id = id;
    this.channelSn = channelSn;
    this.goodsSn = goodsSn;
    this.goodsName = goodsName;
    this.waitSupplyCount = waitSupplyCount;
    this.remainCount = remainCount;
  }
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getChannelSn() {
    return channelSn;
  }

  public void setChannelSn(String channelSn) {
    this.channelSn = channelSn;
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

  public Integer getRemainCount() {
    return remainCount;
  }

  public void setRemainCount(Integer remainCount) {
    this.remainCount = remainCount;
  }
  
}
