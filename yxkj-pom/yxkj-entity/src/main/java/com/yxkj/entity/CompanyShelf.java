package com.yxkj.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 公司货架
 * 
 * @author Andrea
 * @version 2017年9月25日 上午11:09:46
 */
@Entity
@Table(name = "t_comp_shelf")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_comp_shelf_sequence")
public class CompanyShelf extends BaseEntity {


  private static final long serialVersionUID = -4415267250227523543L;

  /**
   * 数量
   */
  public Integer count;

  /**
   * 公司
   */
  private Company comp;

  /**
   * 货架类型
   */
  private ShelfCategory shelfCate;

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public Company getComp() {
    return comp;
  }

  public void setComp(Company comp) {
    this.comp = comp;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public ShelfCategory getShelfCate() {
    return shelfCate;
  }

  public void setShelfCate(ShelfCategory shelfCate) {
    this.shelfCate = shelfCate;
  }


}
