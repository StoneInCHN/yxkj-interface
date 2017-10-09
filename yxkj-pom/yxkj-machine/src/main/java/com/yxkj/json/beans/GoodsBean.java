package com.yxkj.json.beans;

import java.math.BigDecimal;

/**
 *
 * 商品
 * 
 * @author Andrea
 * @version 创建时间：2017年10月9日 下午5:01:01
 * 
 */
public class GoodsBean {

  /**
   * 商品ID
   */
  private Long id;

  /**
   * 商品编号
   */
  private String sn;

  /**
   * 商品名称
   */
  private String name;

  /**
   * 价格
   */
  private BigDecimal price;

  /**
   * 商品图片
   */
  private String picUrl;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getPicUrl() {
    return picUrl;
  }

  public void setPicUrl(String picUrl) {
    this.picUrl = picUrl;
  }

}
