package com.yxkj.json.base;

public class WaitSupplyListRequest {
  
  /**
   * 补货人ID
   */
  private Long userId;
  
  /**
   * 优享空间编号
   */
  private String sceneSn;
  
  /**
   * 类别名称
   */
  private String cateName;
  
  /**
   * 页码
   */
  private String pageNo;
  
  /**
   * 
   */
  private String pageSize;

  /**
   * 货柜编号
   */
  private Long cntrId;
  
  /**
   * 商品编号
   */
  private String goodsSn;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getSceneSn() {
    return sceneSn;
  }

  public void setSceneSn(String sceneSn) {
    this.sceneSn = sceneSn;
  }

  public String getCateName() {
    return cateName;
  }

  public void setCateName(String cateName) {
    this.cateName = cateName;
  }

  public String getPageNo() {
    return pageNo;
  }

  public void setPageNo(String pageNo) {
    this.pageNo = pageNo;
  }
  
  public String getPageSize() {
    return pageSize;
  }

  public void setPageSize(String pageSize) {
    this.pageSize = pageSize;
  }

  public Long getCntrId() {
    return cntrId;
  }

  public void setCntrId(Long cntrId) {
    this.cntrId = cntrId;
  }

  public String getGoodsSn() {
    return goodsSn;
  }

  public void setGoodsSn(String goodsSn) {
    this.goodsSn = goodsSn;
  }
  
}
