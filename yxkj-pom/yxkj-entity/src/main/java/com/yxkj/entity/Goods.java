package com.yxkj.entity;

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

  @ManyToOne(fetch = FetchType.LAZY)
  public GoodsCategory getCategory() {
    return category;
  }

  public void setCategory(GoodsCategory category) {
    this.category = category;
  }



}
