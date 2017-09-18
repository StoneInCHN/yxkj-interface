package com.yxkj.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.OrderEntity;

/**
 * Entity - 商品
 * 
 * @author Andrea
 * @version 2017年9月15日 上午11:22:42
 */
@Entity
@Table(name = "t_goods")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_goods_sequence")
public class Goods extends OrderEntity {

  private static final long serialVersionUID = -2158109459123036967L;

  /**
   * 商品类别
   */
  private GoodsCategory category;

  /**
   * 商品名称
   */
  private String name;

  /**
   * 商品编号
   */
  private String sn;

  /**
   * 成本价
   */
  private BigDecimal costPrice;

  /**
   * 销售价
   */
  private BigDecimal salePrice;



  @Column(scale = 2, precision = 10)
  public BigDecimal getCostPrice() {
    return costPrice;
  }

  public void setCostPrice(BigDecimal costPrice) {
    this.costPrice = costPrice;
  }

  @Column(scale = 2, precision = 10)
  public BigDecimal getSalePrice() {
    return salePrice;
  }

  public void setSalePrice(BigDecimal salePrice) {
    this.salePrice = salePrice;
  }

  @Column(length = 100)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(length = 50)
  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public GoodsCategory getCategory() {
    return category;
  }

  public void setCategory(GoodsCategory category) {
    this.category = category;
  }



}
