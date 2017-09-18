package com.yxkj.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 货柜
 * 
 * @author Andrea
 * @version 2017年9月18日 下午6:19:04
 */
@Entity
@Table(name = "t_vending_container")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_vending_container_sequence")
public class VendingContainer extends BaseEntity {

  private static final long serialVersionUID = -5143795184127147282L;

  /**
   * 售货机
   */
  private VendingMachine machine;


  @ManyToOne(fetch = FetchType.LAZY)
  public VendingMachine getMachine() {
    return machine;
  }

  public void setMachine(VendingMachine machine) {
    this.machine = machine;
  }

}
