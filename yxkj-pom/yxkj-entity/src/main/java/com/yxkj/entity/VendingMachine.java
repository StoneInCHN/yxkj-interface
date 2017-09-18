package com.yxkj.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 无人售货机
 * 
 * @author Andrea
 * @version 2017年9月18日 下午6:13:49
 */
@Entity
@Table(name = "t_vending_machine")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_vending_machine_sequence")
public class VendingMachine extends BaseEntity {

  private static final long serialVersionUID = -6928187434714619174L;

  /**
   * 优享空间场景
   */
  private Scene scene;

  /**
   * 货柜
   */
  private Set<VendingContainer> vendingContainers = new HashSet<VendingContainer>();

  @ManyToOne(fetch = FetchType.LAZY)
  public Scene getScene() {
    return scene;
  }

  public void setScene(Scene scene) {
    this.scene = scene;
  }

  @OneToMany(mappedBy = "container")
  public Set<VendingContainer> getVendingContainers() {
    return vendingContainers;
  }

  public void setVendingContainers(Set<VendingContainer> vendingContainers) {
    this.vendingContainers = vendingContainers;
  }

}
