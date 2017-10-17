package com.yxkj.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.CommonStatus;

/**
 * Entity - 商品
 * 
 * @author Andrea
 * @version 2017年9月15日 上午11:22:42
 */
@Entity
@Table(name = "t_goods", indexes = {@Index(name = "snIndex", columnList = "sn")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_goods_sequence")
public class Goods extends BaseEntity {

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
   * 商品全名
   */
  private String fullName;

  /**
   * 商品规格
   */
  private String spec;

  /**
   * 商品条码
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

  /**
   * 商品状态
   */
  private CommonStatus status;

  // /**
  // * 订单项
  // */
  // private Set<OrderItem> orderItems = new HashSet<OrderItem>();


  /**
   * 商品图片
   */
  private List<GoodsPic> goodsPics = new ArrayList<GoodsPic>();


  @Column(length = 20)
  public String getSpec() {
    return spec;
  }

  public void setSpec(String spec) {
    this.spec = spec;
  }

  @Valid
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  @CollectionTable(name = "t_goods_image")
  public List<GoodsPic> getGoodsPics() {
    return goodsPics;
  }

  public void setGoodsPics(List<GoodsPic> goodsPics) {
    this.goodsPics = goodsPics;
  }

  public CommonStatus getStatus() {
    return status;
  }

  public void setStatus(CommonStatus status) {
    this.status = status;
  }

  // @OneToMany(mappedBy = "goods")
  // public Set<OrderItem> getOrderItems() {
  // return orderItems;
  // }
  //
  // public void setOrderItems(Set<OrderItem> orderItems) {
  // this.orderItems = orderItems;
  // }



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

  @Transient
  public String getFullName() {
    return name + spec;
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
