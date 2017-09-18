package com.yxkj.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 优享空间场景
 * 
 * @author Andrea
 * @version 2017年9月18日 下午6:09:22
 */
@Entity
@Table(name = "t_scene")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_scene_sequence")
public class Scene extends BaseEntity {


  private static final long serialVersionUID = -1649056464007668623L;
  /**
   * 无人售货机
   */
  private Set<VendingMachine> vendingMachines = new HashSet<VendingMachine>();

  @OneToMany(mappedBy = "scene")
  public Set<VendingMachine> getVendingMachines() {
    return vendingMachines;
  }

  public void setVendingMachines(Set<VendingMachine> vendingMachines) {
    this.vendingMachines = vendingMachines;
  }


}
