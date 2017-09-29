package com.yxkj.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 无人货架类型
 * 
 * @author Andrea
 * @version 2017年9月25日 上午10:58:29
 */
@Entity
@Table(name = "t_shelf_category")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_shelf_category_sequence")
public class ShelfCategory extends BaseEntity {


  private static final long serialVersionUID = 9219458199580640954L;

  /**
   * 货架高度(单位:m)
   */
  private String height;

  /**
   * 货架
   */
  private Set<CompanyShelf> goodsShelves = new HashSet<CompanyShelf>();
  
  @JsonProperty
  @Column(length = 10)
  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  @OneToMany(mappedBy = "shelfCate")
  public Set<CompanyShelf> getGoodsShelves() {
    return goodsShelves;
  }

  public void setGoodsShelves(Set<CompanyShelf> goodsShelves) {
    this.goodsShelves = goodsShelves;
  }

}
